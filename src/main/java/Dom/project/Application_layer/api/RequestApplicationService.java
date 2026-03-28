package Dom.project.Application_layer.api;

import Dom.project.Domain_layer.enums.UserRole;
import Dom.project.Web_layer.api.dto.ServiceRequestDto;
import Dom.project.Web_layer.api.dto.UserRequestDto;
import Dom.project.Domain_layer.interfaces.repository.IServiceRequestRepository;
import Dom.project.Domain_layer.interfaces.repository.IUserRepository;
import Dom.project.Domain_layer.model.ServiceRequest;
import Dom.project.Domain_layer.model.User;
import Dom.project.Domain_layer.enums.RequestStatus;
import Dom.project.Domain_layer.exception.DomainException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RequestApplicationService {

    private final IServiceRequestRepository serviceRequestRepository;
    private final IUserRepository userRepository;
    private IUserRepository userRepositoryAdapter;
    private Utils utils;

    public RequestApplicationService(IServiceRequestRepository serviceRequestRepository,
                                     IUserRepository userRepository, Utils utils) {
        this.serviceRequestRepository = serviceRequestRepository;
        this.userRepository = userRepository;
        this.utils = utils;
    }



    public List<UserRequestDto> getCurrentUserRequests() {
        User currentUser = utils.getCurrentUser();
        System.out.println("ID user: " + currentUser.getId());

        List<ServiceRequest> userRequests = serviceRequestRepository.findByCreatorId(currentUser.getId());
        for (ServiceRequest serviceRequest : userRequests){
            System.out.println(serviceRequest.getDescription());
        };

        return userRequests.stream()
                .map(utils::convertToUserRequestDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserRequestDto createUserRequest(UserRequestDto requestDto) {
        User currentUser = utils.getCurrentUser();

        ServiceRequest serviceRequest = new ServiceRequest();
        serviceRequest.setTitle(requestDto.getTitle());
        serviceRequest.setDescription(requestDto.getDescription());
        serviceRequest.setCreator(currentUser);
        serviceRequest.setRequestStatus(RequestStatus.Created);

        ServiceRequest savedRequest = serviceRequestRepository.save(serviceRequest);

        return utils.convertToUserRequestDto(savedRequest);
    }

    public UserRequestDto getUserRequestById(Long id) throws EntityNotFoundException, AccessDeniedException, EntityNotFoundException {
        ServiceRequest serviceRequest = serviceRequestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Запрос с ID " + id + " не найден"));

        User currentUser = utils.getCurrentUser();

        boolean isAdmin = utils.checkAccess(currentUser, List.of(UserRole.Admin));
        boolean isCreator = serviceRequest.getCreator().getId().equals(currentUser.getId());
        boolean isAssigner = serviceRequest.getAssigner() == null ||
                !serviceRequest.getAssigner().getId().equals(currentUser.getId());

        if (!isAdmin && !isCreator && !isAssigner){
            throw new AccessDeniedException("ACCESS DENIED");
        }

        return utils.convertToUserRequestDto(serviceRequest);
    }

    // TODO: Продумать как удаляем пока так
    // Мне кажется не надо сообщять юзеру, кто там может удалить, а кто нет, достаточно access denied просто
    // кроме того поч нельзя в обработке удалить запрос? Мне кажется его ваще удалять не нужно,
    // просто статус в "Закрыт" условный переводить
    // UPDATE: пусть пока ток админ может удалять заявки
    @Transactional
    public void deleteUserRequest(Long id) throws AccessDeniedException, EntityNotFoundException {
        ServiceRequest serviceRequest = serviceRequestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Запрос с ID " + id + " не найден"));

        User currentUser = utils.getCurrentUser();

        boolean isAdmin = utils.checkAccess(currentUser, List.of(UserRole.Admin));
        if (!isAdmin){
            throw new AccessDeniedException("ACCESS DENIED");
        }

        serviceRequestRepository.delete(serviceRequest);
    }

    public UserRequestDto updateRequest(UserRequestDto userRequestDto, Long id) throws AccessDeniedException, EntityNotFoundException{
        ServiceRequest serviceRequest = serviceRequestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Запрос с ID " + id + " не найден"));

        User currentUser = utils.getCurrentUser();

        boolean isAdmin = utils.checkAccess(currentUser, List.of(UserRole.Admin));
        boolean isCreator = serviceRequest.getCreator().getId().equals(currentUser.getId());
        if(!isAdmin && !isCreator){
            throw new AccessDeniedException("ACCESS DENIED");
        }

        if(userRequestDto.getTitle() != null){
            serviceRequest.setTitle(userRequestDto.getTitle());
        }

        if(userRequestDto.getDescription() != null){
            serviceRequest.setDescription(userRequestDto.getDescription());
        }

        serviceRequestRepository.save(serviceRequest);

        return utils.convertToUserRequestDto(serviceRequest);
    }

    public List<ServiceRequestDto> getAllRequests() {
        User currentUser = utils.getCurrentUser();
        if (currentUser.getCompany() == null && currentUser.getRole() != UserRole.Admin) {
            throw new DomainException("User doesnt have company");
        }
        List<ServiceRequest> requests = new ArrayList<ServiceRequest>();
        if (currentUser.getRole() == UserRole.CompanyOwner) {
             requests = serviceRequestRepository.findByCompanyId(currentUser.getCompany().getId());
        }
        if (currentUser.getRole() == UserRole.Worker){
            requests = serviceRequestRepository.findByCreatorId(currentUser.getId());
        }
        if (currentUser.getRole() == UserRole.Admin){
            requests = serviceRequestRepository.findAllRequests();
        }

        return requests.stream()
                .map(utils::convertToServiceRequestDto)
                .toList();
    }

    public ServiceRequestDto getRequestById(Long id) {
        ServiceRequest request = serviceRequestRepository.findById(id)
                .orElseThrow(() -> new DomainException("Запрос не найден"));

        User currentUser = utils.getCurrentUser();
        boolean isCreator = request.getCreator() != null && request.getCreator().getId().equals(currentUser.getId());
        boolean isAssignee = request.getAssigner() != null && request.getAssigner().getId().equals(currentUser.getId());
        boolean sameCompany = currentUser.getCompany() != null &&
                request.getCreator() != null &&
                currentUser.getCompany().getId().equals(request.getCreator().getCompany().getId());

        if (!isCreator && !isAssignee && !sameCompany) {
            throw new DomainException("Нет доступа к этому запросу");
        }

        return utils.convertToServiceRequestDto(request);
    }

    @Transactional
    public ServiceRequestDto updateRequestStatus(Long id, String statusStr) {
        ServiceRequest request = serviceRequestRepository.findById(id)
                .orElseThrow(() -> new DomainException("Запрос не найден"));

        User currentUser = utils.getCurrentUser();
        if (currentUser.getCompany() == null ||
                (request.getCreator() != null && !currentUser.getCompany().getId().equals(request.getCreator().getCompany().getId()))) {
            throw new DomainException("Нет прав на изменение статуса запроса");
        }

        RequestStatus newStatus;
        try {
            newStatus = RequestStatus.valueOf(statusStr);
        } catch (IllegalArgumentException e) {
            throw new DomainException("Некорректный статус: " + statusStr);
        }

        if (newStatus == RequestStatus.InProgress && request.getAssigner() == null) {
            request.setAssigner(currentUser);
        }
        if (newStatus == RequestStatus.Completed && request.getCompletedAt() == null) {
            request.setCompletedAt(LocalDateTime.now());
        }

        request.setRequestStatus(newStatus);
        ServiceRequest updated = serviceRequestRepository.save(request);
        return utils.convertToServiceRequestDto(updated);
    }

    public List<ServiceRequestDto> getCompanyRequests(Long companyId) {

        List<ServiceRequest> requests = serviceRequestRepository.findByCompanyId(companyId);

        return requests.stream()
                .map(utils::convertToServiceRequestDto)
                .toList();
    }
}