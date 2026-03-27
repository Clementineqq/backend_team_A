package Dom.project.Application_layer.api;

import Dom.project.Domain_layer.enums.UserRole;
import Dom.project.Domain_layer.exception.DomainException;
import Dom.project.Domain_layer.model.*;
import Dom.project.Infrastructure_layer.repoAdapters.UserRepositoryAdapter;
import Dom.project.Web_layer.api.dto.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Utils {
    UserRepositoryAdapter userRepository;

    public Utils(UserRepositoryAdapter userRepository){
        this.userRepository = userRepository;
    }

    // converts
    public Address convertToAddress(AddressDto address) {
        Address domainAddress = new Address();

        domainAddress.setCity(address.getCity());
        domainAddress.setFlat(address.getFlat());
        domainAddress.setHouse(address.getHouse());
        domainAddress.setRegion(address.getRegion());
        domainAddress.setStreet(address.getStreet());
        domainAddress.setTotalArea(address.getTotalArea());

        return domainAddress;
    }

    public UserProfileDto convertToUserProfileDto(User user) {
        UserProfileDto dto = new UserProfileDto();
        dto.setId(user.getId());
        dto.setFirstName(user.getName());
        dto.setLastName(user.getLastName());
        dto.setMiddleName(user.getFatherName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone_number());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        dto.setAddress(AddressDto.toDto(user.getAddress()));
        dto.setRole(user.getRole());

        return dto;
    }

    public UserCountersDto convertToUserCountersDto(Counter counter) {
        UserCountersDto dto = new UserCountersDto();
        dto.setId(counter.getId());
        dto.setName(counter.getName());
        dto.setValue(counter.getValue());
        dto.setCreatedAt(counter.getCreatedAt());
        dto.setUpdatedAt(counter.getUpdatedAt());
        dto.setIsApproved(counter.getIsApproved());
        dto.setOwner(convertToUserProfileDto(counter.getOwner()));
        return dto;
    }

    public CompanyProfileDto convertToCompanyProfileDto(Company company, List<User> members, List<User> workers, List<ServiceRequest> requests) {
        CompanyProfileDto dto = new CompanyProfileDto();
        dto.setId(company.getId());
        dto.setCompanyName(company.getName());
        dto.setInn(company.getInn());
        dto.setKpp(company.getKpp());
        dto.setEmail(company.getEmail());
        dto.setDescriprion(company.getDescription());
        if (company.getLegalAddress() != null) {
            dto.setAddress(AddressDto.toDto(company.getLegalAddress()));
        }

        // Преобразуем членов и работников в DTO
        dto.setMembers(members.stream().map(this::convertToUserProfileDto).collect(Collectors.toList()));
        dto.setWorkers(workers.stream().map(this::convertToWorkerDto).collect(Collectors.toList()));
        dto.setCompanyRequests(requests.stream().map(this::convertToServiceRequestDto).collect(Collectors.toList()));

        return dto;
    }

    public CompanyProfileDto convertToCompanyProfileDto(Company company, List<User> members, List<User> workers, List<ServiceRequest> requests, List<User> companyOwner) {
        CompanyProfileDto dto = new CompanyProfileDto();
        dto.setId(company.getId());
        dto.setCompanyName(company.getName());
        dto.setInn(company.getInn());
        dto.setKpp(company.getKpp());
        dto.setEmail(company.getEmail());
        dto.setDescriprion(company.getDescription());
        if (company.getLegalAddress() != null) {
            dto.setAddress(AddressDto.toDto(company.getLegalAddress()));
        }

        // Преобразуем членов и работников в DTO
        dto.setMembers(members.stream().map(this::convertToUserProfileDto).collect(Collectors.toList()));
        dto.setWorkers(workers.stream().map(this::convertToWorkerDto).collect(Collectors.toList()));
        dto.setCompanyRequests(requests.stream().map(this::convertToServiceRequestDto).collect(Collectors.toList()));
        dto.setCompanyOwner(companyOwner.stream().map(this::convertToUserProfileDto).collect(Collectors.toList()));

        return dto;
    }

    public WorkerDto convertToWorkerDto(User user) {
        WorkerDto dto = new WorkerDto();
        dto.setId(user.getId());
        dto.setFirstName(user.getName());
        dto.setLastName(user.getLastName());
        dto.setMiddleName(user.getFatherName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone_number());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        if (user.getAddress() != null) {
            dto.setAddress(AddressDto.toDto(user.getAddress()));
        }
        return dto;
    }

    public ServiceRequestDto convertToServiceRequestDto(ServiceRequest request) {
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

    public Company convertToDomainCompany(CompanyProfileDto profileDto){
        Company company = new Company();
        company.setName(profileDto.getCompanyName());
        company.setInn(profileDto.getInn());
        company.setKpp(profileDto.getKpp());
        company.setEmail(profileDto.getEmail());
        company.setLegalAddress(profileDto.getAddress().toDomain());
        company.setDescription(profileDto.getDescriprion());

        return company;
    }

    public UserRequestDto convertToUserRequestDto(ServiceRequest serviceRequest) {
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

    //todo: протестить
    public Counter convertUserCounterToDomain(UserCountersDto userCounterDto) {
        Counter counter = new Counter(
                userCounterDto.getName(),
                userCounterDto.getValue(),
                convertUserProfileToDomain(userCounterDto.getOwner())
        );

        return counter;
    }

    // возможно нахуй не надо, мб удалим, пока не воркает
    public User convertUserProfileToDomain(UserProfileDto userProfileDto) {
        User user = new User(
                userProfileDto.getPhone(),
                userProfileDto.getEmail(),
                userProfileDto.getPassword(),
                userProfileDto.getFirstName(),
                userProfileDto.getLastName(),
                userProfileDto.getRole()
        );
        return user;
    }

    // other

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getPrincipal().toString();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new DomainException("Текущий пользователь не найден"));
    }

    public List<User> getUsersByRoleFromList(List<User> users, UserRole roleToCheck){
        List<User> resultArray = new ArrayList<>();

        for (User user : users) {
            if (user.getRole() == roleToCheck) {
                resultArray.add(user);
            }
        }

        return resultArray;
    }

    public boolean checkAccess(User user, List<UserRole> requiredRoles){
        return requiredRoles.contains(user.getRole());
    }


}
