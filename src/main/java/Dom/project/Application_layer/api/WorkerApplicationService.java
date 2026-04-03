package Dom.project.Application_layer.api;

import Dom.project.Domain_layer.enums.UserRole;
import Dom.project.Domain_layer.interfaces.repository.IAddressRepository;
import Dom.project.Domain_layer.interfaces.repository.IUserRepository;
import Dom.project.Domain_layer.model.Company;
import Dom.project.Domain_layer.model.User;
import Dom.project.Web_layer.api.dto.AddressDto;
import Dom.project.Web_layer.api.dto.WorkerDto;
import Dom.project.Domain_layer.exception.DomainException;
import jakarta.persistence.EntityExistsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WorkerApplicationService {

    private final PasswordEncoder passwordEncoder;
    private final IUserRepository userRepository;
    private final IAddressRepository addressRepository;
    private Utils utils;

    public WorkerApplicationService(PasswordEncoder passwordEncoder, IUserRepository userRepository, IAddressRepository addressRepository, Utils utils) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.utils = utils;
    }

    // добавил тут id, мб тут поменять как-то, хз как ты его получать хотел
    public List<WorkerDto> getAllWorkers(Long companyId) {
        List<User> workers = userRepository.findAllWorkersByCompanyId(companyId);
        return workers.stream()
                .map(this::convertToWorkerDto)
                .collect(Collectors.toList());
    }

    public WorkerDto getWorkerById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new DomainException("Работник не найден"));
        if (user.getCompany() == null) {
            throw new DomainException("Пользователь не является работником");
        }
        return convertToWorkerDto(user);
    }

    @Transactional
    public WorkerDto createWorker(User currentUser, WorkerDto workerDto) {
        if (currentUser.getCompany() == null) {
            throw new DomainException("Текущий пользователь не принадлежит компании");
        }
        Optional<User> isExistEmail = userRepository.findByEmail(workerDto.getEmail());
        if (isExistEmail.isPresent()){
            throw new EntityExistsException("User with this name already exists");
        }
        Optional<User> isExistPhone = userRepository.findByPhone(workerDto.getPhone());
        if (isExistPhone.isPresent()){
            throw new EntityExistsException("User with this name already exists");
        }

        User newWorker = new User();
        newWorker.setName(workerDto.getFirstName());
        newWorker.setLastName(workerDto.getLastName());
        newWorker.setFatherName(workerDto.getMiddleName());
        newWorker.setEmail(workerDto.getEmail());
        newWorker.setPhone_number(workerDto.getPhone());
        newWorker.setRole(UserRole.Worker);

        String hashedPassword = passwordEncoder.encode(workerDto.getPassword());
        newWorker.setPassword(hashedPassword);

        newWorker.setCompany(currentUser.getCompany());

         if (workerDto.getAddress() != null) {
             newWorker.setAddress(addressRepository.save(utils.convertToAddress(workerDto.getAddress())));
            }

        User saved = userRepository.save(newWorker);
        return convertToWorkerDto(saved);
    }

    @Transactional
    public WorkerDto updateWorker(Long id, WorkerDto worker) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new DomainException("Работник не найден"));


        if (worker.getFirstName() != null) user.setName(worker.getFirstName());
        if (worker.getLastName() != null) user.setLastName(worker.getLastName());
        if (worker.getMiddleName() != null) user.setFatherName(worker.getMiddleName());
        if (worker.getEmail() != null) user.setEmail(worker.getEmail());
        if (worker.getPhone() != null) user.setPhone_number(worker.getPhone());
        if (worker.getPassword() != null) user.setPassword(worker.getPassword());
        if (worker.getAddress() != null) {
            user.updateAddress(utils.convertToAddress(worker.getAddress()));
        }

        User updated = userRepository.save(user);
        return convertToWorkerDto(updated);
    }

    @Transactional
    public void deleteWorker(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new DomainException("Работник не найден"));

        userRepository.delete(user);
    }

    // это тоже надо в utils перенести будет
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
        dto.setRole(user.getRole());
        dto.setCompanyId(user.getCompany().getId());
        if (user.getAddress() != null) {
            dto.setAddress(AddressDto.toDto(user.getAddress()));
        }
        return dto;
    }
}