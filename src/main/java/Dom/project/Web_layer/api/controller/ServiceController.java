package Dom.project.Web_layer.api.controller;

import Dom.project.Web_layer.api.dto.CompanyProfileDto;
import Dom.project.Web_layer.api.dto.ServiceRequestDto;
import Dom.project.Web_layer.api.dto.WorkerDto;
import Dom.project.Application_layer.api.WorkerApplicationService;
import Dom.project.Application_layer.api.CompanyApplicationService;
import Dom.project.Application_layer.api.RequestApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/service")
public class ServiceController {

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
    public ResponseEntity<List<WorkerDto>> getWorkers() {
        List<WorkerDto> workers = workerService.getAllWorkers();
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

    // GET /api/service/company_profile
    @GetMapping("/company_profile")
    public ResponseEntity<CompanyProfileDto> getCompanyProfile() {
        CompanyProfileDto profile = companyService.getCompanyProfile();
        return ResponseEntity.ok(profile);
    }

    // PUT /api/service/company_profile
    @PutMapping("/company_profile")
    public ResponseEntity<CompanyProfileDto> updateCompanyProfile(
            @RequestBody CompanyProfileDto profileDto) {
        CompanyProfileDto updated = companyService.updateCompanyProfile(profileDto);
        return ResponseEntity.ok(updated);
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