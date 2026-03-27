package Dom.project.Application_layer.api;

import Dom.project.Domain_layer.enums.UserRole;
import Dom.project.Domain_layer.interfaces.repository.IAddressRepository;
import Dom.project.Domain_layer.interfaces.repository.ICompanyRepository;
import Dom.project.Domain_layer.interfaces.repository.IUserRepository;
import Dom.project.Domain_layer.interfaces.repository.IServiceRequestRepository;
import Dom.project.Domain_layer.model.Address;
import Dom.project.Domain_layer.model.Company;
import Dom.project.Domain_layer.model.User;
import Dom.project.Domain_layer.model.ServiceRequest;
import Dom.project.Web_layer.api.dto.CompanyProfileDto;
import Dom.project.Web_layer.api.dto.WorkerDto;
import Dom.project.Web_layer.api.dto.UserProfileDto;
import Dom.project.Web_layer.api.dto.ServiceRequestDto;
import Dom.project.Domain_layer.exception.DomainException;
import Dom.project.Web_layer.api.dto.AddressDto;
import jakarta.persistence.EntityExistsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompanyApplicationService {

    private final ICompanyRepository companyRepository;
    private final IUserRepository userRepository;
    private final IServiceRequestRepository serviceRequestRepository;
    private final IAddressRepository addressRepository;
    private final Utils utils;

    public CompanyApplicationService(ICompanyRepository companyRepository,
                                     IUserRepository userRepository,
                                     IServiceRequestRepository serviceRequestRepository,
                                     IAddressRepository addressRepository, Utils utils) throws EntityExistsException{
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
        this.serviceRequestRepository = serviceRequestRepository;
        this.addressRepository = addressRepository;
        this.utils = utils;
    }

    @Transactional
    public CompanyProfileDto createCompanyProfile(CompanyProfileDto profileDto){
        Optional<Company> isExist = companyRepository.findByEmail(profileDto.getEmail());
        if (!isExist.isEmpty()){
            throw new EntityExistsException("Company with this name already exists");
        }

        Company company = utils.convertToDomainCompany(profileDto);

        Address domainAddress = new Address(
                profileDto.getAddress().getStreet(),
                profileDto.getAddress().getHouse(),
                profileDto.getAddress().getFlat(),
                profileDto.getAddress().getCity(),
                profileDto.getAddress().getRegion(),
                profileDto.getAddress().getTotalArea()
        );

        company.setLegalAddress(addressRepository.save(domainAddress));

        Company created = companyRepository.save(company);

        List<User> members = userRepository.findByCompanyId(created.getId());
        List<User> workers = utils.getUsersByRoleFromList(members, UserRole.Worker);
        // Получаем все запросы, связанные с компанией (через пользователей)
        List<ServiceRequest> companyRequests = serviceRequestRepository.findByCompanyId(created.getId()); // добавить в репозиторий

        return utils.convertToCompanyProfileDto(created, members, workers, companyRequests);
    }


    public CompanyProfileDto getCompanyById(Long companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new DomainException("Company not found"));

        // Получаем всех пользователей этой компании (члены + работники)
        List<User> members = userRepository.findByCompanyId(companyId); // нужно добавить в репозиторий
            // Поиск воркеров и владельца, разделено по ролям
        List<User> workers = utils.getUsersByRoleFromList(members, UserRole.Worker);
            //TODO: добавить в DTO CompanyOwner'а

            // Получаем все запросы, связанные с компанией (через пользователей)
        List<ServiceRequest> companyRequests = serviceRequestRepository.findByCompanyId(companyId); // добавить в репозиторий

        return utils.convertToCompanyProfileDto(company, members, workers, companyRequests);
    }

    @Transactional
    public CompanyProfileDto updateCompanyProfile(CompanyProfileDto profileDto) {
        // Проверяем, что текущий пользователь – владелец/админ компании
        User currentUser = utils.getCurrentUser();
        if (currentUser.getCompany() == null) {
            throw new DomainException("User is not connected to company");
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
        //todo: обновить с учетом дто

        // Адрес – преобразуем строку в Address (заглушка)
//        if (profileDto.getAddress() != null && !profileDto.getAddress().isEmpty()) {
//            // company.setLegalAddress(parseAddress(profileDto.getAddress()));
//        }

        Company updated = companyRepository.save(company);

        // Для ответа подгрузим списки
        List<User> members = userRepository.findByCompanyId(updated.getId());
        List<ServiceRequest> requests = serviceRequestRepository.findByCompanyId(updated.getId());
        return utils.convertToCompanyProfileDto(updated, members, members, requests);
    }

    // --- Вспомогательные методы ---
    //TODO: доделать
    private Company getCompanyByWorker(User user){
        /*if user.userRole != UserRole.Worker {
            throw new DomainException("У пользователя недостаточно прав!");
        }*/


        return user.getCompany();
    }
}