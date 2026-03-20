package Dom.project.Application_layer.api;

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
        List<ServiceRequest> userRequests = serviceRequestRepository.findByCreatorId(currentUser.getId());

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

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getPrincipal().toString();

        return this.userRepositoryAdapter.findByEmail(email).get();
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