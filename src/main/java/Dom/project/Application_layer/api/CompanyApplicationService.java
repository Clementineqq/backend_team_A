package Dom.project.Application_layer.api;

import Dom.project.Domain_layer.enums.UserRole;
import Dom.project.Domain_layer.interfaces.repository.ICompanyRepository;
import Dom.project.Domain_layer.interfaces.repository.IUserRepository;
import Dom.project.Domain_layer.interfaces.repository.IServiceRequestRepository;
import Dom.project.Domain_layer.model.Company;
import Dom.project.Domain_layer.model.User;
import Dom.project.Domain_layer.model.ServiceRequest;
import Dom.project.Web_layer.api.dto.CompanyProfileDto;
import Dom.project.Web_layer.api.dto.WorkerDto;
import Dom.project.Web_layer.api.dto.UserProfileDto;
import Dom.project.Web_layer.api.dto.ServiceRequestDto;
import Dom.project.Domain_layer.exception.DomainException;
import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyApplicationService {

    private final ICompanyRepository companyRepository;
    private final IUserRepository userRepository;
    private final IServiceRequestRepository serviceRequestRepository;

    public CompanyApplicationService(ICompanyRepository companyRepository,
                                     IUserRepository userRepository,
                                     IServiceRequestRepository serviceRequestRepository) {
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
        this.serviceRequestRepository = serviceRequestRepository;
    }


    public CompanyProfileDto getCompanyById(Long companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new DomainException("Компания не найдена"));

        // Получаем всех пользователей этой компании (члены + работники)
        List<User> members = userRepository.findByCompanyId(companyId); // нужно добавить в репозиторий
        List<User> workers = members; // пока одинаково, потом можно разделить по ролям

        // Получаем все запросы, связанные с компанией (через пользователей)
        List<ServiceRequest> companyRequests = serviceRequestRepository.findByCompanyId(companyId); // добавить в репозиторий

        return convertToCompanyProfileDto(company, members, workers, companyRequests);
    }

    @Transactional
    public CompanyProfileDto updateCompanyProfile(CompanyProfileDto profileDto) {
        // Проверяем, что текущий пользователь – владелец/админ компании
        User currentUser = getCurrentUser();
        if (currentUser.getCompany() == null) {
            throw new DomainException("Текущий пользователь не принадлежит компании");
        }
        Company company = currentUser.getCompany(); // обновляем свою компанию

        if (profileDto.getCompanyName() != null) {
            company.setName(profileDto.getCompanyName());
        }
        if (profileDto.getInn() != null) {
            company.setInn(profileDto.getInn());
        }
        if (profileDto.getKpp() != null) {
            company.setKpp(profileDto.getKpp());
        }
        if (profileDto.getEmail() != null) {
            company.setEmail(profileDto.getEmail());
        }
        // Адрес – преобразуем строку в Address (заглушка)
        if (profileDto.getAddress() != null && !profileDto.getAddress().isEmpty()) {
            // company.setLegalAddress(parseAddress(profileDto.getAddress()));
        }

        Company updated = companyRepository.save(company);

        // Для ответа подгрузим списки
        List<User> members = userRepository.findByCompanyId(updated.getId());
        List<ServiceRequest> requests = serviceRequestRepository.findByCompanyId(updated.getId());
        return convertToCompanyProfileDto(updated, members, members, requests);
    }

    // --- Вспомогательные методы ---
    //TODO: доделать
    private Company getCompanyByWorker(User user){
        /*if user.userRole != UserRole.Worker {
            throw new DomainException("У пользователя недостаточно прав!");
        }*/


        return user.getCompany();
    }


    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getPrincipal().toString();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new DomainException("Пользователь не найден"));
    }

    private CompanyProfileDto convertToCompanyProfileDto(Company company,
                                                         List<User> members,
                                                         List<User> workers,
                                                         List<ServiceRequest> requests) {
        CompanyProfileDto dto = new CompanyProfileDto();
        dto.setId(company.getId());
        dto.setCompanyName(company.getName());
        dto.setInn(company.getInn());
        dto.setKpp(company.getKpp());
        dto.setEmail(company.getEmail());
        if (company.getLegalAddress() != null) {
            dto.setAddress(company.getLegalAddress().getFullAddress());
        }

        // Преобразуем членов и работников в DTO
        dto.setMembers(members.stream().map(this::convertToUserProfileDto).collect(Collectors.toList()));
        dto.setWorkers(workers.stream().map(this::convertToWorkerDto).collect(Collectors.toList()));
        dto.setCompanyRequests(requests.stream().map(this::convertToServiceRequestDto).collect(Collectors.toList()));

        return dto;
    }

    private UserProfileDto convertToUserProfileDto(User user) {
        UserProfileDto dto = new UserProfileDto();
        dto.setId(user.getId());
        dto.setFirstName(user.getName());
        dto.setLastName(user.getLastName());
        dto.setMiddleName(user.getFatherName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone_number());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        if (user.getAddress() != null) {
            dto.setAddress(user.getAddress().getFullAddress());
        }
        return dto;
    }

    private WorkerDto convertToWorkerDto(User user) {
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
            dto.setAddress(user.getAddress().getFullAddress());
        }
        return dto;
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
}