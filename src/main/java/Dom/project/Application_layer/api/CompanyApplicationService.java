package Dom.project.Application_layer.api;

import Dom.project.Domain_layer.enums.UserRole;
import Dom.project.Domain_layer.interfaces.repository.*;
import Dom.project.Domain_layer.model.*;
import Dom.project.Web_layer.api.dto.*;
import Dom.project.Domain_layer.exception.DomainException;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompanyApplicationService {

    private final ICompanyRepository companyRepository;
    private final IUserRepository userRepository;
    private final IServiceRequestRepository serviceRequestRepository;
    private final IAddressRepository addressRepository;
    private final ICounterRepository counterRepository;
    private final Utils utils;

    public CompanyApplicationService(ICompanyRepository companyRepository,
                                     IUserRepository userRepository,
                                     IServiceRequestRepository serviceRequestRepository,
                                     IAddressRepository addressRepository, ICounterRepository counterRepository, Utils utils) throws EntityExistsException{
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
        this.serviceRequestRepository = serviceRequestRepository;
        this.addressRepository = addressRepository;
        this.counterRepository = counterRepository;
        this.utils = utils;
    }

    @Transactional
    public CompanyProfileDto createCompanyProfile(CompanyProfileDto profileDto){
        Optional<Company> isExist = companyRepository.findByEmail(profileDto.getEmail());
        if (isExist.isPresent()){
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
        List<User> CompanyOwner = utils.getUsersByRoleFromList(members, UserRole.CompanyOwner);
        List<ServiceRequest> companyRequests = serviceRequestRepository.findByCompanyId(created.getId());

        if (!CompanyOwner.isEmpty()) return utils.convertToCompanyProfileDto(company, members, workers, companyRequests, CompanyOwner);
        return utils.convertToCompanyProfileDto(created, members, workers, companyRequests);
    }


    public CompanyProfileDto getCompanyById(Long companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new DomainException("Company not found"));

        List<User> members = userRepository.findByCompanyId(companyId);
        List<User> workers = utils.getUsersByRoleFromList(members, UserRole.Worker);
        List<User> CompanyOwner = utils.getUsersByRoleFromList(members, UserRole.CompanyOwner);
        List<ServiceRequest> companyRequests = serviceRequestRepository.findByCompanyId(companyId);

        if (!CompanyOwner.isEmpty()) return utils.convertToCompanyProfileDto(company, members, workers, companyRequests, CompanyOwner);
        return utils.convertToCompanyProfileDto(company, members, workers, companyRequests);
    }

    @Transactional
    public CompanyProfileDto updateCompanyProfile(CompanyProfileDto profileDto, Long company_id) {
        Company company = companyRepository.findById(company_id)
                .orElseThrow(() -> new EntityNotFoundException("Cant find counter with id" + company_id));

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
        if(profileDto.getDescriprion() != null){
            company.setDescription(profileDto.getDescriprion());
        }

        if (profileDto.getAddress() != null) {
            Address newAddress = utils.convertToAddress(profileDto.getAddress());
            company.updateAddress(newAddress);
            addressRepository.save(company.getLegalAddress());
        }

        Company updated = companyRepository.save(company);

        List<User> members = userRepository.findByCompanyId(updated.getId());
        List<User> workers = utils.getUsersByRoleFromList(members, UserRole.Worker);
        List<User> CompanyOwner = utils.getUsersByRoleFromList(members, UserRole.CompanyOwner);

        List<ServiceRequest> requests = serviceRequestRepository.findByCompanyId(updated.getId());
        if (!CompanyOwner.isEmpty()) return utils.convertToCompanyProfileDto(updated, members, workers, requests, CompanyOwner);
        return utils.convertToCompanyProfileDto(updated, members, workers, requests);
    }

    public UserCountersDto updateCounter(User worker, Long idToUpdate) throws EntityNotFoundException, AccessDeniedException {
        Counter counterFromRepo = counterRepository.findById(idToUpdate)
                .orElseThrow(() -> new EntityNotFoundException("Cant find counter with id" + idToUpdate));

        if (!Objects.equals(worker.getCompany().getId(), counterFromRepo.getOwner().getCompany().getId())){
            throw new AccessDeniedException("ACCESS DENIED");
        }

        counterFromRepo.setIsApproved(Boolean.TRUE);

        counterRepository.save(counterFromRepo);
        return utils.convertToUserCountersDto(counterFromRepo);
    }

    // Получить список всех компаний
    // Возвращает без списокв участников, воркеров и реквестов
    // Если это нужно, то лучше взять ID и сделать запрос повторный на конкретную компанию
    public List<CompanyProfileDto> getCompanies() throws AccessDeniedException {
        User currentUser = utils.getCurrentUser();

        boolean isAdmin = utils.checkAccess(currentUser, List.of(UserRole.Admin));
        if (!isAdmin) throw new AccessDeniedException("ACCESS DENIED");

        List<Company> companies = companyRepository.findAll();
        List<CompanyProfileDto> res_companies = new ArrayList<>();

        for (Company company : companies){
            CompanyProfileDto companyProfileDto = utils.convertToCompanyProfileDto(
                    company, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            res_companies.add(companyProfileDto);
        }

        return res_companies;
    }

    public List<UserCountersDto> getAllCountersInCompany() throws AccessDeniedException {
        User currentUser = utils.getCurrentUser();

        boolean hasAccess = utils.checkAccess(currentUser, List.of(UserRole.Admin, UserRole.Worker, UserRole.CompanyOwner));
        if (!hasAccess) throw new AccessDeniedException("ACCESS DENIED");

        List<Counter> counters = counterRepository.findAllByCompanyId(currentUser.getCompany().getId());

        return counters.stream()
                .map(utils::convertToUserCountersDto)
                .peek(dto -> dto.getOwner().setAddress(null))
                .peek(dto -> dto.getOwner().setPhone(null))// аналогично, странно как-то возвращать все данные о юзере миллион раз
                .collect(Collectors.toList());
    }
}