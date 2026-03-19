package Dom.project.Web_layer.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import Dom.project.Application_layer.auth.AuthService;
import Dom.project.Domain_layer.model.User;
import Dom.project.Web_layer.auth.dto.AuthResponse;
import Dom.project.Web_layer.auth.dto.LoginRequest;
import Dom.project.Web_layer.auth.dto.RegisterRequest;
import Dom.project.Web_layer.utils.JwtUtils;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtUtils jwtUtils;

    public AuthController(AuthService authService, JwtUtils jwtUtils) {
        this.authService = authService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        User registeredUser = authService.register(
                request.getEmail(),
                request.getName(),
                request.getSurname(),
                request.getPhone(),
                request.getPassword(),
                request.getId_company(),
                request.getAddress()
        );
        
        String token = jwtUtils.generateToken(registeredUser.getEmail());
        
        return ResponseEntity.ok(new AuthResponse(
            "Регистрация успешна!", 
            token,
            registeredUser.getEmail(),
            registeredUser.getFullName()
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        User loggedUser = authService.login(request.getEmail(), request.getPassword());
        
        String token = jwtUtils.generateToken(loggedUser.getEmail());
        
        return ResponseEntity.ok(new AuthResponse(
            "Логин успешен!", 
            token,
            loggedUser.getEmail(),
            loggedUser.getFullName()
        ));
    }

    @PostMapping("/logout/{userId}")
    public ResponseEntity<String> logout(@PathVariable Long userId) {
        authService.logout(userId);
        return ResponseEntity.ok("Выход выполнен");
    }

    @GetMapping("/test")
    public String test(){
        System.out.println("Попал в тест");
        return "TEST OK";
    }
}