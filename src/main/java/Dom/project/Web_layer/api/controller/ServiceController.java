package Dom.project.Web_layer.api.controller;

import Dom.project.Application_layer.api.*;
import Dom.project.Domain_layer.enums.UserRole;
import Dom.project.Domain_layer.model.User;
import Dom.project.Web_layer.api.dto.CompanyProfileDto;
import Dom.project.Web_layer.api.dto.ServiceRequestDto;
import Dom.project.Web_layer.api.dto.UserCountersDto;
import Dom.project.Web_layer.api.dto.WorkerDto;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/service")
public class ServiceController {
    private final WorkerApplicationService workerService;
    private final CompanyApplicationService companyService;
    private final RequestApplicationService requestService;
    private final Utils utils;

    public ServiceController(WorkerApplicationService workerService,
                             CompanyApplicationService companyService,
                             RequestApplicationService requestService, Utils utils) {
        this.workerService = workerService;
        this.companyService = companyService;
        this.requestService = requestService;
        this.utils = utils;
    }

    // GET /api/service/company_profile/workers/{companyId}
    @GetMapping("/company_profile/workers/{companyId}")
    public ResponseEntity<?> getWorkers(@PathVariable Long companyId) {
        try {
            User currUser = utils.getCurrentUser();
            if (!utils.checkAccess(currUser, List.of(UserRole.CompanyOwner, UserRole.Admin))) {
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body("ACCESS DENIED");
            }

            if (!currUser.getCompany().getId().equals(companyId) && currUser.getRole() == UserRole.CompanyOwner) {
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body("ACCESS DENIED");
            }

            List<WorkerDto> workers = workerService.getAllWorkers(companyId);
            return ResponseEntity.ok(workers);
            } catch (EntityNotFoundException e){
                    return ResponseEntity
                            .status(HttpStatus.NOT_FOUND)
                            .body("NOT FOUND");
            } catch (Exception e){
                    System.out.println(e.getMessage());
                    return ResponseEntity
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("SERVER ERROR");
            }

    }

    // GET /api/service/workers/{id}
    @GetMapping("/workers/{id}")
    public ResponseEntity<?> getWorkerById(@PathVariable Long id) {
        try {
            User currUser = utils.getCurrentUser();
            if (!utils.checkAccess(currUser, List.of(UserRole.CompanyOwner, UserRole.Admin))){
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body("ACCESS DENIED");
            }
            WorkerDto worker = workerService.getWorkerById(id);

            if (!Objects.equals(currUser.getCompany().getId(), worker.getCompanyId()) && currUser.getRole() == UserRole.CompanyOwner){
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body("ACCESS DENIED");
            }


            return ResponseEntity.ok(worker);
        }  catch (EntityNotFoundException e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("NOT FOUND");
        }   catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("SERVER ERROR");
        }

    }

    // POST /api/service/workers
    @PostMapping("/workers")
    public ResponseEntity<?> createWorker(@RequestBody WorkerDto workerDto) {
        try {
            User currUser = utils.getCurrentUser();
            if (!utils.checkAccess(currUser, List.of(UserRole.CompanyOwner, UserRole.Admin))){
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body("ACCESS DENIED");
            }

            WorkerDto created = workerService.createWorker(currUser, workerDto);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (EntityExistsException e) {
                return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("ALREADY EXISTS");
        }  catch (Exception e){
                System.out.println(e.getMessage());
                return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("SERVER ERROR");
        }
    }

    // PUT /api/service/workers/{id}
    @PutMapping("/workers/{id}")
    public ResponseEntity<?> updateWorker(@PathVariable Long id,
                                                  @RequestBody WorkerDto workerDto) {
        try {
            User currUser = utils.getCurrentUser();
            if (!utils.checkAccess(currUser, List.of(UserRole.CompanyOwner, UserRole.Admin))){
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body("ACCESS DENIED");
            }
            WorkerDto worker = workerService.getWorkerById(id);

            if (!Objects.equals(currUser.getCompany().getId(), worker.getCompanyId()) && currUser.getRole() == UserRole.CompanyOwner){
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body("ACCESS DENIED");
            }
            WorkerDto updated = workerService.updateWorker(id, workerDto);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(updated);
        }  catch (EntityNotFoundException e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("NOT FOUND");
        }  catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("SERVER ERROR");
        }
    }

    // DELETE /api/service/workers/{id}
    @DeleteMapping("/workers/{id}")
    public ResponseEntity<?> deleteWorker(@PathVariable Long id) {

        try{
            User currUser = utils.getCurrentUser();
            if (!utils.checkAccess(currUser, List.of(UserRole.CompanyOwner, UserRole.Admin))){
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body("ACCESS DENIED");
            }
            WorkerDto worker = workerService.getWorkerById(id);

            if (!Objects.equals(currUser.getCompany().getId(), worker.getCompanyId()) && currUser.getRole() == UserRole.CompanyOwner){
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body("ACCESS DENIED");
            }
            workerService.deleteWorker(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("NOT FOUND");
        }  catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("SERVER ERROR");
        }
    }

    // GET /api/service/company_profile/{id}
    @GetMapping("/company_profile/{companyId}")
    public ResponseEntity<?> getCompanyProfileById(@PathVariable Long companyId) {
        try {
            User currUser = utils.getCurrentUser();

            if (!utils.checkAccess(currUser, List.of(UserRole.Worker, UserRole.CompanyOwner, UserRole.Admin))) {
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body("ACCESS DENIED");
            }

            if (!Objects.equals(currUser.getCompany().getId(), companyId) && utils.checkAccess(currUser, List.of(UserRole.CompanyOwner, UserRole.Worker))) {
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body("ACCESS DENIED");
            }

            CompanyProfileDto profile = companyService.getCompanyById(companyId);

            return ResponseEntity.ok(profile);
        } catch (EntityNotFoundException e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("NOT FOUND");
        }  catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("SERVER ERROR");
        }
    }

    //GET /api/service/company_profile
    @GetMapping("/company_profile")
    public ResponseEntity<?> getCompanies(){
        try {
            List<CompanyProfileDto> companies = companyService.getCompanies();
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(companies);

        } catch (AccessDeniedException e) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(e.getMessage());

        } catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("SERVER ERROR");
        }
    }

    // PUT /api/service/company_profile/{company_id}
    @PutMapping("/company_profile/{company_id}")
    public ResponseEntity<?> updateCompanyProfile(
            @PathVariable Long company_id,
            @RequestBody CompanyProfileDto profileDto)  {
        try {
            User currUser = utils.getCurrentUser();


            if (!utils.checkAccess(currUser, List.of( UserRole.CompanyOwner, UserRole.Admin))) {
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body("ACCESS DENIED");
            }
            if (!Objects.equals(currUser.getCompany().getId(), company_id) && currUser.getRole() == UserRole.CompanyOwner){
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body("ACCESS DENIED");
            }

            CompanyProfileDto updated = companyService.updateCompanyProfile(profileDto, company_id);

            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("NOT FOUND");
        }  catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("SERVER ERROR");
        }
    }

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
    public ResponseEntity<?> getServiceRequests() {
        try{
            if(!utils.checkAccess(utils.getCurrentUser(), List.of(UserRole.Admin, UserRole.Worker, UserRole.CompanyOwner))){
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body("ACCESS DENIED");
            }
            List<ServiceRequestDto> requests = requestService.getAllRequests();
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(requests);
        } catch (EntityNotFoundException e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("NOT FOUND");
        } catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("SERVER ERROR");
        }
    }

    // GET /api/service/requests/company/{companyId}
    @GetMapping("/requests/company/{companyId}")
    public ResponseEntity<?> getServiceRequests(@PathVariable Long companyId) {
        try{
            if(!utils.checkAccess(utils.getCurrentUser(), List.of(UserRole.Admin))){
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body("ACCESS DENIED");
            }
            List<ServiceRequestDto> requests = requestService.getCompanyRequests(companyId);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(requests);
        } catch (EntityNotFoundException e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("NOT FOUND");
        } catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("SERVER ERROR");
        }
    }


    // GET /api/service/requests/{id}
    @GetMapping("/requests/{id}")
    public ResponseEntity<?> getServiceRequestById(@PathVariable Long id) {
        try{
            if(!utils.checkAccess(utils.getCurrentUser(), List.of(UserRole.Admin, UserRole.Worker, UserRole.CompanyOwner))){
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body("ACCESS DENIED");
            }
            ServiceRequestDto request = requestService.getRequestById(id);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(request);
        } catch (EntityNotFoundException e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("NOT FOUND");
        } catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("SERVER ERROR");
        }
    }

    // PUT /api/service/requests/{id}/status
    @PutMapping("/requests/{id}/status")
    public ResponseEntity<?> updateRequestStatus(
            @PathVariable Long id,
            @RequestBody String status) {
        try{
            if(!utils.checkAccess(utils.getCurrentUser(), List.of(UserRole.Admin, UserRole.Worker, UserRole.CompanyOwner))){
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body("ACCESS DENIED");
            }
            ServiceRequestDto updated = requestService.updateRequestStatus(id, status);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(updated);
        } catch (EntityNotFoundException e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("NOT FOUND");
        } catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("SERVER ERROR");
        }
    }

    //PUT /api/service/counters/{id}
    @PutMapping("counters/{id}")
    public ResponseEntity<?> updateUserCounters(
            @PathVariable Long id) {
        try{
            User correntUser = utils.getCurrentUser();

            if(!utils.checkAccess(utils.getCurrentUser(), List.of(UserRole.Admin, UserRole.Worker, UserRole.CompanyOwner))){
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body("ACCESS DENIED");
            }
            UserCountersDto updated = companyService.updateCounter(correntUser, id);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(updated);
        } catch (EntityNotFoundException e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("NOT FOUND");
        } catch (AccessDeniedException e){
            return  ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("ACCESS DENIED");
        } catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("SERVER ERROR");
        }
    }
}