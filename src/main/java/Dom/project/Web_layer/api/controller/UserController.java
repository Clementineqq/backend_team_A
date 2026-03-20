package Dom.project.Web_layer.api.controller;

import Dom.project.Web_layer.api.dto.UserProfileDto;
import Dom.project.Web_layer.api.dto.UserRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    // GET /api/users/counters
    @GetMapping("/counters")
    public String getUserCounters() {

        return "Счетчики";
    }

    // GET /api/users/profile
    @GetMapping("/profile")
    public String getUserProfile() {

        return "Профиль пользователя";
    }

    // PUT /api/users/profile
    @PutMapping("/profile")
    public String updateUserProfile(
            @RequestBody UserProfileDto profileDto) {

        return "Изменения в профиле";
    }

    // GET /api/users/requests
    @GetMapping("/requests")
    public String getUserRequests() {


        return "Запросы";
    }

    // POST /api/users/requests
    @PostMapping("/requests")
    public String createUserRequest(
            @RequestBody UserRequestDto requestDto) {

        return "Новый Запрос";
    }

    // GET /api/users/requests/{id}
    @GetMapping("/requests/{id}")
    public String getUserRequestById(@PathVariable Long id) {

        return "Id реквеста";
    }

    // DELETE /api/users/requests/{id}
    @DeleteMapping("/requests/{id}")
    public ResponseEntity<Void> deleteUserRequest(@PathVariable Long id) {
        return ResponseEntity.noContent().build();
    }

}