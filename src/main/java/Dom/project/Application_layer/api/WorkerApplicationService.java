package Dom.project.Application_layer.api;

import Dom.project.Domain_layer.interfaces.repository.IUserRepository;
import Dom.project.Domain_layer.model.User;
import Dom.project.Web_layer.api.dto.AddressDto;
import Dom.project.Web_layer.api.dto.WorkerDto;
import Dom.project.Domain_layer.exception.DomainException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkerApplicationService {

    private final IUserRepository userRepository;

    public WorkerApplicationService(IUserRepository userRepository) {
        this.userRepository = userRepository;
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
    public WorkerDto createWorker(WorkerDto workerDto) {
        User currentUser = getCurrentUser();
        if (currentUser.getCompany() == null) {
            throw new DomainException("Текущий пользователь не принадлежит компании");
        }

        User newWorker = new User();
        newWorker.setName(workerDto.getFirstName());
        newWorker.setLastName(workerDto.getLastName());
        newWorker.setFatherName(workerDto.getMiddleName());
        newWorker.setEmail(workerDto.getEmail());
        newWorker.setPhone_number(workerDto.getPhone());
        newWorker.setPassword(workerDto.getPassword()); // в реальности пароль хешируется
        newWorker.setCompany(currentUser.getCompany()); // привязываем к той же компании

        // Если есть адрес – установить (в DTO адрес строкой, надо преобразовать)
      //  if (workerDto.getAddress() != null && !workerDto.getAddress().isEmpty()) {
            // Преобразование строки в Address – оставим заглушку
            // newWorker.setAddress(parseAddress(workerDto.getAddress()));
      //  }

        User saved = userRepository.save(newWorker);
        return convertToWorkerDto(saved);
    }

    @Transactional
    public WorkerDto updateWorker(Long id, WorkerDto workerDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new DomainException("Работник не найден"));

        // Проверка прав: только сам работник или его компания могут редактировать
        User currentUser = getCurrentUser();
        if (!user.getId().equals(currentUser.getId()) &&
                (currentUser.getCompany() == null || !currentUser.getCompany().getId().equals(user.getCompany().getId()))) {
            throw new DomainException("Нет прав на редактирование этого работника");
        }

        if (workerDto.getFirstName() != null) user.setName(workerDto.getFirstName());
        if (workerDto.getLastName() != null) user.setLastName(workerDto.getLastName());
        if (workerDto.getMiddleName() != null) user.setFatherName(workerDto.getMiddleName());
        if (workerDto.getEmail() != null) user.setEmail(workerDto.getEmail());
        if (workerDto.getPhone() != null) user.setPhone_number(workerDto.getPhone());
        if (workerDto.getPassword() != null) user.setPassword(workerDto.getPassword());
        // Адрес – аналогично созданию

        User updated = userRepository.save(user);
        return convertToWorkerDto(updated);
    }

    @Transactional
    public void deleteWorker(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new DomainException("Работник не найден"));

        User currentUser = getCurrentUser();
        if (!currentUser.getId().equals(user.getId()) &&
                (currentUser.getCompany() == null || !currentUser.getCompany().getId().equals(user.getCompany().getId()))) {
            throw new DomainException("Нет прав на удаление этого работника");
        }

        // Можно либо физически удалить, либо пометить как удалённого
        userRepository.delete(user);
    }

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getPrincipal().toString();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new DomainException("Текущий пользователь не найден"));
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
        dto.setRole(user.getRole());
        //TODO: Доделать
        //dto.setCompanyProfileDto(user.getCompany());
        if (user.getAddress() != null) {
            dto.setAddress(AddressDto.toDto(user.getAddress()));
        }
        return dto;
    }
}