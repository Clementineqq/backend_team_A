package Dom.project.Web_layer.api.controller;

import Dom.project.Domain_layer.enums.UserRole;
import Dom.project.Domain_layer.model.User;
import Dom.project.Web_layer.api.dto.CompanyProfileDto;
import Dom.project.Web_layer.api.dto.ServiceRequestDto;
import Dom.project.Web_layer.api.dto.WorkerDto;
import Dom.project.Application_layer.api.WorkerApplicationService;
import Dom.project.Application_layer.api.CompanyApplicationService;
import Dom.project.Application_layer.api.RequestApplicationService;
import jakarta.persistence.EntityExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/service")
public class ServiceController {
    // TODO: добавить роль во все файлы

    private final WorkerApplicationService workerService;
    private final CompanyApplicationService companyService;
    private final RequestApplicationService requestService;

    public ServiceController(WorkerApplicationService workerService,
                             CompanyApplicationService companyService,
                             RequestApplicationService requestService) {
        this.workerService = workerService;
        this.companyService = companyService;
        this.requestService = requestService;
    }

    // GET /api/service/workers
    @GetMapping("/workers")
    public ResponseEntity<List<WorkerDto>> getWorkers(@PathVariable Long companyId) {
        List<WorkerDto> workers = workerService.getAllWorkers(companyId);
        return ResponseEntity.ok(workers);
    }

    // GET /api/service/workers/{id}
    @GetMapping("/workers/{id}")
    public ResponseEntity<WorkerDto> getWorkerById(@PathVariable Long id) {
        WorkerDto worker = workerService.getWorkerById(id);
        return ResponseEntity.ok(worker);
    }

    // POST /api/service/workers
    @PostMapping("/workers")
    public ResponseEntity<WorkerDto> createWorker(@RequestBody WorkerDto workerDto) {
        WorkerDto created = workerService.createWorker(workerDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // PUT /api/service/workers/{id}
    @PutMapping("/workers/{id}")
    public ResponseEntity<WorkerDto> updateWorker(@PathVariable Long id,
                                                  @RequestBody WorkerDto workerDto) {
        WorkerDto updated = workerService.updateWorker(id, workerDto);
        return ResponseEntity.ok(updated);
    }

    // DELETE /api/service/workers/{id}
    @DeleteMapping("/workers/{id}")
    public ResponseEntity<Void> deleteWorker(@PathVariable Long id) {
        workerService.deleteWorker(id);
        return ResponseEntity.noContent().build();
    }

    // TODO: дописать
    @GetMapping("/company_profile/{companyId}")
    public ResponseEntity<?> getCompanyProfileById(@PathVariable Long companyId) {
        User currUser = companyService.getCurrentUser();
        if (!companyService.checkAccess(currUser, List.of(UserRole.Worker, UserRole.CompanyOwner, UserRole.Admin))){
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("ACCESS DENIED");
        }
        // TODO: добавить овнера
        if (currUser.getCompany().getId() != companyId && currUser.getRole() == UserRole.Worker){
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("ACCESS DENIED, YOU'RE WORKER IN ANOTHER COMPANY");
        }

        CompanyProfileDto profile = companyService.getCompanyById(companyId);

        if (profile == null){
            return ResponseEntity
                    .notFound()
                    .build();
        }

        return ResponseEntity.ok(profile);
    }

    // PUT /api/service/company_profile
    @PutMapping("/company_profile")
    public ResponseEntity<CompanyProfileDto> updateCompanyProfile(
            @RequestBody CompanyProfileDto profileDto) {
        CompanyProfileDto updated = companyService.updateCompanyProfile(profileDto);
        return ResponseEntity.ok(updated);
    }

    // желательно чтобы все методы в зависимости от експшена выкидывали ошибку
    @PostMapping("/company_profile/register")
    public ResponseEntity<?> createCompanyProfile(
            @RequestBody CompanyProfileDto profileDto) {
        if(!companyService.checkAccess(companyService.getCurrentUser(), List.of(UserRole.Admin))){
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("ACCESS DENIED");
        }

        try {
            CompanyProfileDto company = companyService.createCompanyProfile(profileDto);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("SUCCESS " + company.getCompanyName());

        } catch (EntityExistsException e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("ALREADY EXISTS");
        } catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    // GET /api/service/requests
    @GetMapping("/requests")
    public ResponseEntity<List<ServiceRequestDto>> getServiceRequests() {
        List<ServiceRequestDto> requests = requestService.getAllRequests();
        return ResponseEntity.ok(requests);
    }

    // GET /api/service/requests/{id}
    @GetMapping("/requests/{id}")
    public ResponseEntity<ServiceRequestDto> getServiceRequestById(@PathVariable Long id) {
        ServiceRequestDto request = requestService.getRequestById(id);
        return ResponseEntity.ok(request);
    }

    // PUT /api/service/requests/{id}/status
    @PutMapping("/requests/{id}/status")
    public ResponseEntity<ServiceRequestDto> updateRequestStatus(
            @PathVariable Long id,
            @RequestBody String status) {
        ServiceRequestDto updated = requestService.updateRequestStatus(id, status);
        return ResponseEntity.ok(updated);
    }

}