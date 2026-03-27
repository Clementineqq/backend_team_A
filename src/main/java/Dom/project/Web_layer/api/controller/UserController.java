package Dom.project.Web_layer.api.controller;

import Dom.project.Application_layer.api.CompanyApplicationService;
import Dom.project.Domain_layer.enums.UserRole;
import Dom.project.Domain_layer.model.ServiceRequest;
import Dom.project.Domain_layer.model.User;
import Dom.project.Web_layer.api.dto.ServiceRequestDto;
import Dom.project.Web_layer.api.dto.UserCountersDto;
import Dom.project.Web_layer.api.dto.UserProfileDto;
import Dom.project.Web_layer.api.dto.UserRequestDto;
import Dom.project.Application_layer.api.UserApplicationService;
import Dom.project.Application_layer.api.RequestApplicationService;
import jakarta.persistence.EntityNotFoundException;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.aspectj.apache.bcel.generic.RET;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserApplicationService userService;
    private final RequestApplicationService userRequestService;
    private final CompanyApplicationService companyApplicationService;

    public UserController(UserApplicationService userService,
                          RequestApplicationService userRequestService, CompanyApplicationService companyApplicationService) {
        this.userService = userService;
        this.userRequestService = userRequestService;
        this.companyApplicationService = companyApplicationService;
    }

    // GET /api/users/counters
    @GetMapping("/counters")
    public ResponseEntity<List<UserCountersDto>> getUserCounters() {
        List<UserCountersDto> counters = userService.getUserCounters();
        return ResponseEntity.ok(counters);

    }

    @GetMapping("/counters/{counterId}")
    public ResponseEntity<?> getCounterById(@PathVariable Long id){
        try{
            //todo: *
            UserCountersDto counter = userService.getCounter(id);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(counter);
        } catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("SERVER ERROR");
        }
    }


    // create counter
    // POST /api/users/counters
    @PostMapping("/counters")
    public ResponseEntity<?> createCounter(UserCountersDto userCounterDto) {
        try {
            userService.createCounter(userCounterDto);
            return ResponseEntity.ok(userCounterDto);
        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("SERVER ERROR");
        }
    }

    // PUT api/users/counters/{id}
    @PutMapping("/counters/{id}")
    public ResponseEntity<?> updateCounter(UserCountersDto userCountersDto, @PathVariable Long id){
        try{
            //todo: *
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("CREATED COUNTER");
        } catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("SERVER ERROR");
        }
    }


    // delete counter
    // DELETE /api/users/counters/{id}
    @DeleteMapping("/counters/{id}")
    public ResponseEntity<?> deleteCounter(@PathVariable Long id) {
        try {
            userService.deleteCounter(id);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("DELETED");
        } catch (EntityNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("NOT FOUND USER WITH ID " + id);
        } catch (AccessDeniedException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }


    // GET /api/users/profile
    @GetMapping("/profile")
    public ResponseEntity<UserProfileDto> getUserProfile() {
        UserProfileDto profile = userService.getCurrentUserProfile();
        return ResponseEntity.ok(profile);
    }

    // PUT /api/users/profile
    @PutMapping("/profile")
    public ResponseEntity<UserProfileDto> updateUserProfile(
            @RequestBody UserProfileDto profileDto) {
        UserProfileDto updated = userService.updateUserProfile(profileDto);
        return ResponseEntity.ok(updated);
    }

    // DELETE /api/users/profile
    @DeleteMapping("/profile/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("DELETED");

        } catch (AccessDeniedException e) {
            return ResponseEntity.
                    status(HttpStatus.FORBIDDEN)
                    .body(e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    // GET /api/users/requests
    @GetMapping("/requests")
    public ResponseEntity<List<UserRequestDto>> getUserRequests() {
        List<UserRequestDto> requests = userRequestService.getCurrentUserRequests();
        return ResponseEntity.ok(requests);
    }

    // POST /api/users/requests
    @PostMapping("/requests")
    public ResponseEntity<UserRequestDto> createUserRequest(
            @RequestBody UserRequestDto requestDto) {
        UserRequestDto created = userRequestService.createUserRequest(requestDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // GET /api/users/requests/{id}
    @GetMapping("/requests/{id}")
    public ResponseEntity<UserRequestDto> getUserRequestById(@PathVariable Long id) {
        UserRequestDto request = userRequestService.getUserRequestById(id);
        return ResponseEntity.ok(request);
    }

    // DELETE /api/users/requests/{id}
    @DeleteMapping("/requests/{id}")
    public ResponseEntity<Void> deleteUserRequest(@PathVariable Long id) {
        userRequestService.deleteUserRequest(id);
        return ResponseEntity.noContent().build();
    }

    //тестовый метод, можешь поредачить взять отсюда часть
    @GetMapping("/requests/get/{id}")
    public ResponseEntity<?> test(@PathVariable Long id) {
        try {
            List<ServiceRequestDto> requests = userRequestService.getAllRequests();

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(requests);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("SERVER ERROR");
        }
    }
}