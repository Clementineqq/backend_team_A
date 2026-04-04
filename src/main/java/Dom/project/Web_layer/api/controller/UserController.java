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
import jakarta.persistence.EntityExistsException;
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

    @GetMapping("/counters/{id}")
    public ResponseEntity<?> getCounterById(@PathVariable Long id){
        try{
            UserCountersDto counter = userService.getCounter(id);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(counter);
        } catch (EntityNotFoundException e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("NOT FOUND");
        } catch (AccessDeniedException e){
            return  ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("ACCESS DENIED");
        } catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("SERVER ERROR");
        }
    }


    // create counter
    // POST /api/users/counters
    @PostMapping("/counters")
    public ResponseEntity<?> createCounter(@RequestBody UserCountersDto userCounterDto) {
        try {
            UserCountersDto created = userService.createCounter(userCounterDto);
            created.setOwner(null); // чтобы личную инфу юзера не отправлять.. как будто лишнее

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(created);
        } catch (EntityExistsException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("COUNTER WITH THIS TYPE ALREADY EXISTS");
        } catch (NullPointerException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("FIELD CANT BE EMPTY");
        }

        catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("SERVER ERROR");
        }
    }

    // PUT api/users/counters/{id}
    @PutMapping("/counters/{id}")
    public ResponseEntity<?> updateCounter(@RequestBody UserCountersDto userCountersDto, @PathVariable Long id){
        try{
            UserCountersDto updated = userService.updateCounter(userCountersDto, id);
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
                    .body("NOT FOUND COUNTER WITH ID " + id);
        } catch (AccessDeniedException e) {
            System.out.println(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("ACCESS DENIED");
        } catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("SERVER ERROR");
        }
    }

    // GET /api/users/profile
    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile() {
      try {
          UserProfileDto profile = userService.getCurrentUserProfile();
          return ResponseEntity.ok(profile);
      } catch (Exception e){
          System.out.println(e.getMessage());
          return ResponseEntity
                  .status(HttpStatus.INTERNAL_SERVER_ERROR)
                  .body("SERVER ERROR");
      }
    }

    // PUT /api/users/profile
    @PutMapping("/profile")
    public ResponseEntity<?> updateUserProfile(
            @RequestBody UserProfileDto profileDto) {
        try {
            UserProfileDto updated = userService.updateUserProfile(profileDto);
            return ResponseEntity.ok(updated);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("SERVER ERROR");
        }
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
        } catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("SERVER ERROR");
        }
    }

    // GET /api/users/requests
    @GetMapping("/requests")
    public ResponseEntity<?> getUserRequests() {
        try {
            List<UserRequestDto> requests = userRequestService.getCurrentUserRequests();
            return ResponseEntity.ok(requests);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("SERVER ERROR");
        }
    }

    // POST /api/users/requests
    @PostMapping("/requests")
    public ResponseEntity<?> createUserRequest(
            @RequestBody UserRequestDto requestDto) {
       try {
           UserRequestDto created = userRequestService.createUserRequest(requestDto);
           return new ResponseEntity<>(created, HttpStatus.CREATED);
       } catch (Exception e){
           System.out.println(e.getMessage());
           return ResponseEntity
                   .status(HttpStatus.INTERNAL_SERVER_ERROR)
                   .body("SERVER ERROR");
       }
    }

    // GET /api/users/requests/{id}
    @GetMapping("/requests/{id}")
    public ResponseEntity<?> getUserRequestById(@PathVariable Long id) {
        try {
            UserRequestDto request = userRequestService.getUserRequestById(id);
            return ResponseEntity.ok(request);
        } catch (AccessDeniedException e){
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(e.getMessage());
        }catch (EntityNotFoundException e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("NOT FOUND");
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("SERVER ERROR");
        }
    }

    // DELETE /api/users/requests/{id}
    @DeleteMapping("/requests/{id}")
    public ResponseEntity<?> deleteUserRequest(@PathVariable Long id) {
        try {
            userRequestService.deleteUserRequest(id);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("DELETED");
        } catch (AccessDeniedException e){
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(e.getMessage());
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

    @PutMapping("requests/{id}")
    public ResponseEntity<?> updateRequest(@RequestBody UserRequestDto userRequestDto, @PathVariable Long id){
        try{
            UserRequestDto updated = userRequestService.updateRequest(userRequestDto, id);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(updated);

        } catch (AccessDeniedException e){
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(e.getMessage());
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
}