package Dom.project.Web_layer.api.controller;

import Dom.project.Web_layer.api.dto.CompanyProfileDto;
import Dom.project.Web_layer.api.dto.ServiceRequestDto;
import Dom.project.Web_layer.api.dto.WorkerDto;
import Dom.project.Web_layer.api.dto.*;
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
    public String getWorkers() {

        return "Работники";
    }

    // GET /api/service/workers/{id}
    @GetMapping("/workers/{id}")
    public String getWorkerById(@PathVariable Long id) {

        return "Рабочий по Id";
    }

    // POST /api/service/workers
    @PostMapping("/workers")
    public String createWorker(@RequestBody WorkerDto workerDto) {

        return "Создание работника";
    }

    // PUT /api/service/workers/{id}
    @PutMapping("/workers/{id}")
    public String updateWorker(@PathVariable Long id,
                                                  @RequestBody WorkerDto workerDto) {

        return "Изменения работника по ID";
    }

    // DELETE /api/service/workers/{id}
    @DeleteMapping("/workers/{id}")
    public ResponseEntity<Void> deleteWorker(@PathVariable Long id) {
        return ResponseEntity.noContent().build();
    }

    // GET /api/service/company_profile
    @GetMapping("/company_profile")
    public String getCompanyProfile() {

        return "Получение профиля компании";
    }

    // PUT /api/service/company_profile
    @PutMapping("/company_profile")
    public String updateCompanyProfile(
            @RequestBody CompanyProfileDto profileDto) {

        return "Профиль компании изменения";
    }

    // GET /api/service/requests
    @GetMapping("/requests")
    public String getServiceRequests() {

        return "Запросы ";
    }

    // GET /api/service/requests/{id}
    @GetMapping("/requests/{id}")
    public String getServiceRequestById(@PathVariable Long id) {

        return "Запрос по Id";
    }

}