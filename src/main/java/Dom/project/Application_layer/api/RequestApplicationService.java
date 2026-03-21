package Dom.project.Application_layer.api;

import Dom.project.Web_layer.api.dto.ServiceRequestDto;
import Dom.project.Web_layer.api.dto.UserRequestDto;
import Dom.project.Domain_layer.interfaces.repository.IServiceRequestRepository;
import Dom.project.Domain_layer.interfaces.repository.IUserRepository;
import Dom.project.Domain_layer.model.ServiceRequest;
import Dom.project.Domain_layer.model.User;
import Dom.project.Domain_layer.enums.RequestStatus;
import Dom.project.Domain_layer.exception.DomainException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RequestApplicationService {

    private final IServiceRequestRepository serviceRequestRepository;
    private final IUserRepository userRepository;
    private IUserRepository userRepositoryAdapter;

    public RequestApplicationService(IServiceRequestRepository serviceRequestRepository,
                                     IUserRepository userRepository) {
        this.serviceRequestRepository = serviceRequestRepository;
        this.userRepository = userRepository;
    }

    public List<UserRequestDto> getCurrentUserRequests() {
        User currentUser = getCurrentUser();
        System.out.println("ID user: " + currentUser.getId());

        List<ServiceRequest> userRequests = serviceRequestRepository.findByCreatorId(currentUser.getId());
        for (ServiceRequest serviceRequest : userRequests){
            System.out.println(serviceRequest.getDescription());
        };

        return userRequests.stream()
                .map(this::convertToUserRequestDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserRequestDto createUserRequest(UserRequestDto requestDto) {
        User currentUser = getCurrentUser();

        ServiceRequest serviceRequest = new ServiceRequest();
        serviceRequest.setTitle(requestDto.getTitle());
        serviceRequest.setDescription(requestDto.getDescription());
        serviceRequest.setCreator(currentUser);
        serviceRequest.setRequestStatus(RequestStatus.Created);

        ServiceRequest savedRequest = serviceRequestRepository.save(serviceRequest);

        return convertToUserRequestDto(savedRequest);
    }

    public UserRequestDto getUserRequestById(Long id) {
        ServiceRequest serviceRequest = serviceRequestRepository.findById(id)
                .orElseThrow(() -> new DomainException("Запрос с ID " + id + " не найден"));

        User currentUser = getCurrentUser();

        if (!serviceRequest.getCreator().getId().equals(currentUser.getId()) &&
                (serviceRequest.getAssigner() == null ||
                        !serviceRequest.getAssigner().getId().equals(currentUser.getId()))) {
            throw new DomainException("У вас нет доступа к этому запросу");
        }

        return convertToUserRequestDto(serviceRequest);
    }

    // TODO: Продумать как удаляем пока так
    @Transactional
    public void deleteUserRequest(Long id) {
        ServiceRequest serviceRequest = serviceRequestRepository.findById(id)
                .orElseThrow(() -> new DomainException("Запрос с ID " + id + " не найден"));

        User currentUser = getCurrentUser();

        if (!serviceRequest.getCreator().getId().equals(currentUser.getId())) {
            throw new DomainException("Только создатель может удалить запрос");
        }

        if (serviceRequest.getRequestStatus() != RequestStatus.Created) {
            throw new DomainException("Нельзя удалить запрос, который уже обрабатывается");
        }

        serviceRequestRepository.delete(serviceRequest);
    }

    public List<ServiceRequestDto> getAllRequests() {
        User currentUser = getCurrentUser();
        if (currentUser.getCompany() == null) {
            throw new DomainException("Текущий пользователь не состоит в компании");
        }
        // Получаем все запросы всех сотрудников компании
        List<ServiceRequest> requests = serviceRequestRepository.findByCompanyId(currentUser.getCompany().getId());
        return requests.stream()
                .map(this::convertToServiceRequestDto)
                .toList();
    }

    public ServiceRequestDto getRequestById(Long id) {
        ServiceRequest request = serviceRequestRepository.findById(id)
                .orElseThrow(() -> new DomainException("Запрос не найден"));

        User currentUser = getCurrentUser();
        boolean isCreator = request.getCreator() != null && request.getCreator().getId().equals(currentUser.getId());
        boolean isAssignee = request.getAssigner() != null && request.getAssigner().getId().equals(currentUser.getId());
        boolean sameCompany = currentUser.getCompany() != null &&
                request.getCreator() != null &&
                currentUser.getCompany().getId().equals(request.getCreator().getCompany().getId());

        if (!isCreator && !isAssignee && !sameCompany) {
            throw new DomainException("Нет доступа к этому запросу");
        }

        return convertToServiceRequestDto(request);
    }


    @Transactional
    public ServiceRequestDto updateRequestStatus(Long id, String statusStr) {
        ServiceRequest request = serviceRequestRepository.findById(id)
                .orElseThrow(() -> new DomainException("Запрос не найден"));

        User currentUser = getCurrentUser();
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
        return convertToServiceRequestDto(updated);
    }

    // --- Вспомогательные методы ---

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getPrincipal().toString();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new DomainException("Пользователь не найден"));
    }

    private ServiceRequestDto convertToServiceRequestDto(ServiceRequest request) {
        ServiceRequestDto dto = new ServiceRequestDto();
        dto.setId(request.getId());
        dto.setTitle(request.getTitle());
        dto.setDescription(request.getDescription());
        dto.setStatus(request.getRequestStatus().toString());
        if (request.getCreator() != null) {
            dto.setCreator(request.getCreator().getFullName());
        }
        if (request.getAssigner() != null) {
            dto.setAssigner(request.getAssigner().getFullName());
        }
        dto.setCreatedAt(request.getCreatedAt());
        dto.setUpdatedAt(request.getUpdatedAt());
        dto.setCompletedAt(request.getCompletedAt());
        return dto;
    }

    private UserRequestDto convertToUserRequestDto(ServiceRequest serviceRequest) {
        UserRequestDto dto = new UserRequestDto();
        dto.setId(serviceRequest.getId());
        dto.setTitle(serviceRequest.getTitle());
        dto.setDescription(serviceRequest.getDescription());
        dto.setStatus(serviceRequest.getStringRequestStatus());
        dto.setCreatedAt(serviceRequest.getCreatedAt());
        dto.setUpdatedAt(serviceRequest.getUpdatedAt());

        if (serviceRequest.getCreator() != null) {
            dto.setCreatorId(serviceRequest.getCreator().getId());
            dto.setCreatorName(serviceRequest.getCreator().getFullName());
        }

        if (serviceRequest.getAssigner() != null) {
            dto.setAssigneeId(serviceRequest.getAssigner().getId());
            dto.setAssigneeName(serviceRequest.getAssigner().getFullName());
        }

        if (serviceRequest.getCompletedAt() != null) {
            dto.setCompletedAt(serviceRequest.getCompletedAt());
        }

        return dto;
    }
}