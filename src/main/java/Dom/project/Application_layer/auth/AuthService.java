package Dom.project.Application_layer.auth;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import Dom.project.Domain_layer.exception.InvalidUserException;
import Dom.project.Domain_layer.interfaces.repository.IUserRepository;
import Dom.project.Domain_layer.model.User;

@Service
public class AuthService {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(IUserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public User register(String email, String name, String surname, String phone, String password) {
        System.out.println("регистрация");

        if (userRepository.findByEmail(email).isPresent() ||
            userRepository.findByPhone(phone).isPresent()) {
            throw InvalidUserException.userAlreadyExists(phone, email);
        }

        // тута хешируем пароль
        String hashedPassword = passwordEncoder.encode(password);
        User user = new User(phone, email, hashedPassword, name, surname);

        return userRepository.save(user);
    }

    public User login(String email, String password) {
        System.out.println("логин");

        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            throw InvalidUserException.userNotFoundByEmail(email);
        }

        User user = userOpt.get();

         if (!passwordEncoder.matches(password, user.getPassword())) {
            throw InvalidUserException.incorrectPassword();
        }

        return user;
    }

    public void logout(Long userId) {
        System.out.println("выход из userId: " + userId);
        
    }
}