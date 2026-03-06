package Dom.project.Application_layer.auth;

import org.springframework.stereotype.Service;
import Dom.project.Domain_layer.model.User;

@Service
public class AuthService {

    public User register(String email, String name, String surname, String phone) {
        System.out.println("рега");
        return null; // потом здесь вернём User
    }

    public User login(String email, String password) {
        System.out.println("логин");
        return null; // тоже самое
    }

    public void logout(Long userId) {
        System.out.println("выход");
    }
}