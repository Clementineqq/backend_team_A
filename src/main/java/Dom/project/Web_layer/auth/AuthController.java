package Dom.project.Web_layer.auth;

import org.springframework.web.bind.annotation.*;
import Dom.project.Application_layer.auth.AuthService;
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
    public String register(@RequestBody RegisterRequest request) {
        authService.register(request.getEmail(), request.getName(), request.getSurname(), request.getPhone());
        return "register skeleton";
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        authService.login(request.getEmail(), request.getPassword());
        return "login skeleton";
    }

    @PostMapping("/logout/{userId}")
    public String logout(@PathVariable Long userId) {
        authService.logout(userId);
        return "logout skeleton";
    }
}