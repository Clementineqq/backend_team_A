    package Dom.project.Web_layer.api.controller;

    import Dom.project.Web_layer.api.dto.UserCountersDto;
    import Dom.project.Web_layer.api.dto.UserProfileDto;
    import Dom.project.Web_layer.api.dto.UserRequestDto;
    import Dom.project.Application_layer.api.UserApplicationService;
    import Dom.project.Application_layer.api.RequestApplicationService;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;

    @RestController
    @RequestMapping("/api/users")
    public class UserController {

        private final UserApplicationService userService;
        private final RequestApplicationService userRequestService;

        public UserController(UserApplicationService userService,
                              RequestApplicationService userRequestService) {
            this.userService = userService;
            this.userRequestService = userRequestService;
        }

        // GET /api/users/counters
        /*
        Пока что не рабочий
        //
        @GetMapping("/counters")
        public ResponseEntity<List<UserCountersDto>> getUserCounters() {
            List<UserCountersDto> counters = userService.getUserCounters();
            return ResponseEntity.ok(counters);

        }
         */

        // GET /api/users/profile
        // TODO: Протестить
        @GetMapping("/profile")
        public ResponseEntity<UserProfileDto> getUserProfile() {
            UserProfileDto profile = userService.getCurrentUserProfile();
            return ResponseEntity.ok(profile);
        }

        // PUT /api/users/profile
        //TODO: Протестить но без адреса
        @PutMapping("/profile")
        public ResponseEntity<UserProfileDto> updateUserProfile(
                @RequestBody UserProfileDto profileDto) {
            UserProfileDto updated = userService.updateUserProfile(profileDto);
            return ResponseEntity.ok(updated);
        }

        // GET /api/users/requests
        // Не рабочий
        @GetMapping("/requests")
        public ResponseEntity<List<UserRequestDto>> getUserRequests() {
            List<UserRequestDto> requests = userRequestService.getCurrentUserRequests();
            return ResponseEntity.ok(requests);
        }

        // POST /api/users/requests
        //TODO: Протестить
        @PostMapping("/requests")
        public ResponseEntity<UserRequestDto> createUserRequest(
                @RequestBody UserRequestDto requestDto) {
            UserRequestDto created = userRequestService.createUserRequest(requestDto);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        }

        // GET /api/users/requests/{id}
        // TODO: Протестить
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
    }