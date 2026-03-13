package Dom.project.Web_layer.api.controller;

import Dom.project.Web_layer.api.dto.UserCountersDto;
import Dom.project.Web_layer.api.dto.UserProfileDto;
import Dom.project.Web_layer.api.dto.UserRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    // GET /api/users/counters
    @GetMapping("/counters")
    public ResponseEntity<List<UserCountersDto>> getUserCounters() {
        // Здесь будет вызов сервиса для получения счетчиков пользователя
        UserProfileDto userProfile = new UserProfileDto(1L, "Иван", "Петров",
                "ivan@mail.com", "+7 (999) 123-45-67");

        List<UserCountersDto> counters = List.of(
                new UserCountersDto("requests_count", "15",
                        LocalDateTime.now(), LocalDateTime.now(), true, userProfile),
                new UserCountersDto("notifications_count", "3",
                        LocalDateTime.now(), LocalDateTime.now(), true, userProfile)
        );
        return ResponseEntity.ok(counters);
    }

    // GET /api/users/profile
    @GetMapping("/profile")
    public ResponseEntity<UserProfileDto> getUserProfile() {
        // Здесь будет вызов сервиса для получения профиля пользователя
        UserProfileDto profile = new UserProfileDto(
                1L, "Иван", "Петров", "ivan@mail.com", "+7 (999) 123-45-67"
        );
        return ResponseEntity.ok(profile);
    }

    // PUT /api/users/profile
    @PutMapping("/profile")
    public ResponseEntity<UserProfileDto> updateUserProfile(
            @RequestBody UserProfileDto profileDto) {
        // Здесь будет вызов сервиса для обновления профиля
        UserProfileDto updated = new UserProfileDto(
                profileDto.getId(),
                profileDto.getFirstName(),
                profileDto.getLastName(),
                profileDto.getEmail(),
                profileDto.getPhone()
        );
        return ResponseEntity.ok(updated);
    }

    // GET /api/users/requests
    @GetMapping("/requests")
    public ResponseEntity<List<UserRequestDto>> getUserRequests() {
        // Здесь будет вызов сервиса для получения заявок пользователя
        List<UserRequestDto> requests = List.of(
                new UserRequestDto(1L, "Ремонт телефона",
                        "Разбит экран", "IN_PROGRESS",
                        "Иван Петров", "Петров П.А.",
                        "Ожидаем запчасти",
                        LocalDateTime.now().minusDays(3),
                        LocalDateTime.now().minusDays(2), null)
        );
        return ResponseEntity.ok(requests);
    }

    // POST /api/users/requests
    @PostMapping("/requests")
    public ResponseEntity<UserRequestDto> createUserRequest(
            @RequestBody UserRequestDto requestDto) {
        // Здесь будет вызов сервиса для создания заявки
        UserRequestDto created = new UserRequestDto(
                System.currentTimeMillis(),
                requestDto.getTitle(),
                requestDto.getDescription(),
                "NEW",
                requestDto.getCreator(),
                null,
                null,
                LocalDateTime.now(),
                LocalDateTime.now(),
                null
        );
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // GET /api/users/requests/{id}
    @GetMapping("/requests/{id}")
    public ResponseEntity<UserRequestDto> getUserRequestById(@PathVariable Long id) {
        // Здесь будет вызов сервиса для получения заявки по ID
        UserRequestDto request = new UserRequestDto(
                id, "Ремонт телефона",
                "Разбит экран", "IN_PROGRESS",
                "Иван Петров", "Петров П.А.",
                "Ожидаем запчасти",
                LocalDateTime.now().minusDays(3),
                LocalDateTime.now().minusDays(2), null
        );
        return ResponseEntity.ok(request);
    }

    // DELETE /api/users/requests/{id}
    @DeleteMapping("/requests/{id}")
    public ResponseEntity<Void> deleteUserRequest(@PathVariable Long id) {
        // Здесь будет вызов сервиса для удаления заявки
        return ResponseEntity.noContent().build();
    }
}