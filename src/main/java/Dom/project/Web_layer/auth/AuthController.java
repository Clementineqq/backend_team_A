package Dom.project.Web_layer.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Dom.project.Application_layer.auth.AuthService;
import Dom.project.Domain_layer.model.User;
import Dom.project.Web_layer.auth.dto.AuthResponse;
import Dom.project.Web_layer.auth.dto.CompanyLoginRequest;
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

    @Transactional
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        User registeredUser = authService.register(
                request.getEmail(),
                request.getName(),
                request.getSurname(),
                request.getPhone(),
                request.getPassword(),
                request.getId_company(),
                request.getAddress(),
                request.getRole()
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

    @PostMapping("/company/login")
    public ResponseEntity<AuthResponse> companyLogin(@RequestBody CompanyLoginRequest request) {
        User companyUser = authService.loginCompany(request.getEmail(), request.getPassword());
        
        String token = jwtUtils.generateToken(companyUser.getEmail());
        
        return ResponseEntity.ok(new AuthResponse(
            "Вход компании успешен!", 
            token,
            companyUser.getEmail(),
            companyUser.getName() // Имя компании
        ));
    }

}