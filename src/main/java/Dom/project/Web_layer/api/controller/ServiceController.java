package Dom.project.Web_layer.api.controller;

import Dom.project.Application_layer.api.Utils;
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
    private final WorkerApplicationService workerService;
    private final CompanyApplicationService companyService;
    private final RequestApplicationService requestService;
    private Utils utils;

    // todo: проверку счетчика

    public ServiceController(WorkerApplicationService workerService,
                             CompanyApplicationService companyService,
                             RequestApplicationService requestService, Utils utils) {
        this.workerService = workerService;
        this.companyService = companyService;
        this.requestService = requestService;
        this.utils = utils;
    }

    // GET /api/service/workers
    @GetMapping("/company_profile/workers/{companyId}")
    public ResponseEntity<?> getWorkers(@PathVariable Long companyId) {
        User currUser = utils.getCurrentUser();
        if (!utils.checkAccess(currUser, List.of(UserRole.CompanyOwner, UserRole.Admin))){
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("ACCESS DENIED");
        }

        if (!currUser.getCompany().getId().equals(companyId) && currUser.getRole() == UserRole.CompanyOwner){
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("ACCESS DENIED");
        }

        List<WorkerDto> workers = workerService.getAllWorkers(companyId);
        return ResponseEntity.ok(workers);
    }

    // GET /api/service/workers/{id}
    // TODO: доделать | саньку не трогать
    @GetMapping("/workers/{id}")
    public ResponseEntity<?> getWorkerById(@PathVariable Long id) {
        User currUser = utils.getCurrentUser();
        if (!utils.checkAccess(currUser, List.of(UserRole.CompanyOwner, UserRole.Admin))){
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("ACCESS DENIED");
        }
        WorkerDto worker = workerService.getWorkerById(id);

        if (currUser.getCompany().getId() != worker.getCompanyProfileDto().getId() && currUser.getRole() == UserRole.CompanyOwner){
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("ACCESS DENIED");
        }


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

    // GET /api/service/company_profile/{id}
    @GetMapping("/company_profile/{companyId}")
    public ResponseEntity<?> getCompanyProfileById(@PathVariable Long companyId) {
        User currUser = utils.getCurrentUser();
        if (!utils.checkAccess(currUser, List.of(UserRole.Worker, UserRole.CompanyOwner, UserRole.Admin))){
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("ACCESS DENIED");
        }

        boolean isOwner  = currUser.getRole() == UserRole.Worker;
        boolean isWorker = currUser.getRole() == UserRole.CompanyOwner;

        if (currUser.getCompany().getId() != companyId && (isOwner || isWorker)){
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("ACCESS DENIED");
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
    // POST /api/service/company_profile/register
    @PostMapping("/company_profile/register")
    public ResponseEntity<?> createCompanyProfile(
            @RequestBody CompanyProfileDto profileDto) {
        if(!utils.checkAccess(utils.getCurrentUser(), List.of(UserRole.Admin))){
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