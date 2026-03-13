package Dom.project.Application_layer.auth;

import java.util.Optional;

import org.springframework.stereotype.Service;

import Dom.project.Domain_layer.exception.InvalidUserException;
import Dom.project.Domain_layer.interfaces.repository.IUserRepository;
import Dom.project.Domain_layer.model.User;

@Service
public class AuthService {

    private final IUserRepository userRepository;

     public AuthService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(String email, String name, String surname, String phone, String password) {
        System.out.println("рега");

         if (userRepository.findByEmail(email).isPresent() ||
            userRepository.findByPhone(phone).isPresent()) {
            throw InvalidUserException.userAlreadyExists(phone, email);
        }

         User user = new User(phone, email, password, name, surname);

         return userRepository.save(user);
    }

    public User login(String email, String password) {
        System.out.println("логин");

        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            throw InvalidUserException.userNotFoundByEmail(email);
        }

        User user = userOpt.get();

        if (!user.checkPassword(password)) {
            throw InvalidUserException.incorrectPassword();
        }

        return user;
    }

    public void logout(Long userId) {
        System.out.println("выход");
        
    }
}