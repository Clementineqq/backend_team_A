package Dom.project.Web_layer.auth;

import Dom.project.Application_layer.api.Utils;
import jakarta.persistence.EntityExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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

    // POST /auth/register
    @Transactional
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
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
        } catch (EntityExistsException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("ALREADY EXISTS");
        }  catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("SERVER ERROR");
        }

    }

    // POST /auth/login
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

    // GET /auth/logout
    @GetMapping("/logout")
    public ResponseEntity<String> logout() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("LOGOUT COMPLETE");
    }
}