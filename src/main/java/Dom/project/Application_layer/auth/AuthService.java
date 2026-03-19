package Dom.project.Application_layer.auth;

import java.util.Optional;

import Dom.project.Domain_layer.interfaces.repository.IAddressRepository;
import Dom.project.Domain_layer.interfaces.repository.ICompanyRepository;
import Dom.project.Domain_layer.model.Address;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import Dom.project.Domain_layer.exception.InvalidUserException;
import Dom.project.Domain_layer.interfaces.repository.IUserRepository;
import Dom.project.Domain_layer.model.User;
import org.yaml.snakeyaml.events.Event;

@Service
public class AuthService {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ICompanyRepository companyRepository;
    private final IAddressRepository addressRepository;

    public AuthService(IUserRepository userRepository, ICompanyRepository companyRepository, IAddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.addressRepository = addressRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();

    }
    // Вот это пока не трогай
    public User register(String email, String name, String surname, String phone, String password, String idCompany, String idAddress) {
        System.out.println("регистрация");

        // if (userRepository.findByEmail(email).isPresent() ||
        //     userRepository.findByPhone(phone).isPresent()) {
        //     throw InvalidUserException.userAlreadyExists(phone, email);
        // }

        // тута хешируем пароль
        String hashedPassword = passwordEncoder.encode(password);
        User user = new User(phone, email, hashedPassword, name, surname);

        // это надобно в БД по id найти.
        user.setAddress(addressRepository.findById(Long.parseLong(idAddress)).orElseThrow(() ->
                new EntityNotFoundException("Cant find address with ID:" + idAddress)));

        //   Address address = new Address();
        //user.setAddress(addressRepository.save(address));

        user.setCompany(companyRepository.findById(Long.parseLong(idCompany)).orElseThrow(() ->
                new EntityNotFoundException("Cant find company with ID: " + idAddress)));

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