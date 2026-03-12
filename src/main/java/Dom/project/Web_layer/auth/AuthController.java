package Dom.project.Web_layer.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Dom.project.Application_layer.auth.AuthService;
import Dom.project.Domain_layer.model.User;
import Dom.project.Web_layer.auth.dto.LoginRequest;
import Dom.project.Web_layer.auth.dto.RegisterRequest;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        User registeredUser = authService.register(
                request.getEmail(),
                request.getName(),
                request.getSurname(),
                request.getPhone(),
                request.getPassword()
        );
        return ResponseEntity.ok("Регистрация успешна! Пользователь: " + registeredUser.getFullName());
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        User loggedUser = authService.login(request.getEmail(), request.getPassword());
        return ResponseEntity.ok("Логин успешен! Пользователь: " + loggedUser.getFullName());
    }

    @PostMapping("/logout/{userId}")
    public ResponseEntity<String> logout(@PathVariable Long userId) {
        authService.logout(userId);
        return ResponseEntity.ok("Выход выполнен");
    }
}