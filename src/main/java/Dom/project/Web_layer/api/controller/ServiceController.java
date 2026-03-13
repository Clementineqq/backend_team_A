package Dom.project.Web_layer.api.controller;

import Dom.project.Web_layer.api.dto.CompanyProfileDto;
import Dom.project.Web_layer.api.dto.ServiceRequestDto;
import Dom.project.Web_layer.api.dto.WorkerDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/service")
public class ServiceController {

    // GET /api/service/workers
    @GetMapping("/workers")
    public ResponseEntity<List<WorkerDto>> getWorkers() {
        // Здесь будет вызов сервиса для получения списка работников
        List<WorkerDto> workers = List.of(
                new WorkerDto(1L, "Иван", "Петров", "Иванович",
                        "ivan.petrov@company.com", "+7 (999) 123-45-67",
                        "password123", "г. Москва, ул. Ленина, д.1",
                        LocalDateTime.now().minusYears(2), LocalDateTime.now(), null)
        );
        return ResponseEntity.ok(workers);
    }

    // GET /api/service/workers/{id}
    @GetMapping("/workers/{id}")
    public ResponseEntity<WorkerDto> getWorkerById(@PathVariable Long id) {
        // Здесь будет вызов сервиса для получения работника по ID
        WorkerDto worker = new WorkerDto(id, "Иван", "Петров", "Иванович",
                "ivan.petrov@company.com", "+7 (999) 123-45-67",
                "password123", "г. Москва, ул. Ленина, д.1",
                LocalDateTime.now().minusYears(2), LocalDateTime.now(), null);
        return ResponseEntity.ok(worker);
    }

    // POST /api/service/workers
    @PostMapping("/workers")
    public ResponseEntity<WorkerDto> createWorker(@RequestBody WorkerDto workerDto) {
        // Здесь будет вызов сервиса для создания работника
        WorkerDto created = new WorkerDto(
                1L,
                workerDto.getFirstName(),
                workerDto.getLastName(),
                workerDto.getMiddleName(),
                workerDto.getEmail(),
                workerDto.getPhone(),
                workerDto.getPassword(),
                workerDto.getAddress(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                null
        );
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // PUT /api/service/workers/{id}
    @PutMapping("/workers/{id}")
    public ResponseEntity<WorkerDto> updateWorker(@PathVariable Long id,
                                                  @RequestBody WorkerDto workerDto) {
        // Здесь будет вызов сервиса для обновления работника
        WorkerDto updated = new WorkerDto(
                id,
                workerDto.getFirstName(),
                workerDto.getLastName(),
                workerDto.getMiddleName(),
                workerDto.getEmail(),
                workerDto.getPhone(),
                workerDto.getPassword(),
                workerDto.getAddress(),
                workerDto.getCreatedAt(),
                LocalDateTime.now(),
                workerDto.getWorkerRequests()
        );
        return ResponseEntity.ok(updated);
    }

    // DELETE /api/service/workers/{id}
    @DeleteMapping("/workers/{id}")
    public ResponseEntity<Void> deleteWorker(@PathVariable Long id) {
        // Здесь будет вызов сервиса для удаления работника
        return ResponseEntity.noContent().build();
    }

    // GET /api/service/company_profile
    @GetMapping("/company_profile")
    public ResponseEntity<CompanyProfileDto> getCompanyProfile() {
        // Здесь будет вызов сервиса для получения профиля компании
        CompanyProfileDto profile = new CompanyProfileDto(
                1L,
                "ООО Сервис-Про",
                "г. Москва, ул. Ленина, д. 1",
                "7701234567",
                "770101001",
                "info@service-pro.ru"
        );
        return ResponseEntity.ok(profile);
    }

    // PUT /api/service/company_profile
    @PutMapping("/company_profile")
    public ResponseEntity<CompanyProfileDto> updateCompanyProfile(
            @RequestBody CompanyProfileDto profileDto) {
        // Здесь будет вызов сервиса для обновления профиля компании
        CompanyProfileDto updated = new CompanyProfileDto(
                profileDto.getId(),
                profileDto.getCompanyName(),
                profileDto.getAddress(),
                profileDto.getInn(),
                profileDto.getKpp(),
                profileDto.getEmail()
        );
        return ResponseEntity.ok(updated);
    }

    // GET /api/service/requests
    @GetMapping("/requests")
    public ResponseEntity<List<ServiceRequestDto>> getServiceRequests() {
        // Здесь будет вызов сервиса для получения всех заявок
        List<ServiceRequestDto> requests = List.of(
                new ServiceRequestDto(1L, "Ремонт оборудования",
                        "Не работает стиральная машина",
                        "NEW", "user@mail.com", "Иванов И.И.",
                        "Требуется диагностика",
                        LocalDateTime.now().minusDays(2),
                        LocalDateTime.now().minusDays(2), null)
        );
        return ResponseEntity.ok(requests);
    }

    // GET /api/service/requests/{id}
    @GetMapping("/requests/{id}")
    public ResponseEntity<ServiceRequestDto> getServiceRequestById(@PathVariable Long id) {
        // Здесь будет вызов сервиса для получения заявки по ID
        ServiceRequestDto request = new ServiceRequestDto(
                id, "Ремонт оборудования",
                "Не работает стиральная машина",
                "NEW", "user@mail.com", "Иванов И.И.",
                "Требуется диагностика",
                LocalDateTime.now().minusDays(2),
                LocalDateTime.now().minusDays(2), null
        );
        return ResponseEntity.ok(request);
    }
}