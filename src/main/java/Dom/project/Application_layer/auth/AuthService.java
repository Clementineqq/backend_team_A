package Dom.project.Application_layer.auth;

import java.util.Optional;

import Dom.project.Domain_layer.enums.UserRole;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import Dom.project.Domain_layer.exception.InvalidUserException;
import Dom.project.Domain_layer.interfaces.repository.IAddressRepository;
import Dom.project.Domain_layer.interfaces.repository.ICompanyRepository;
import Dom.project.Domain_layer.interfaces.repository.IUserRepository;
import Dom.project.Domain_layer.model.Address;
import Dom.project.Domain_layer.model.Company;
import Dom.project.Domain_layer.model.User;
import Dom.project.Web_layer.api.dto.AddressDto;
import jakarta.persistence.EntityNotFoundException;

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
    public User register(String email, String name, String surname, String phone, String password, String idCompany, AddressDto address, UserRole role) {
        System.out.println("регистрация");

        // if (userRepository.findByEmail(email).isPresent() ||
        //     userRepository.findByPhone(phone).isPresent()) {
        //     throw InvalidUserException.userAlreadyExists(phone, email);
        // }

        // тута хешируем пароль
        String hashedPassword = passwordEncoder.encode(password);
        User user = new User(phone, email, hashedPassword, name, surname);
        user.setRole(role);

        Address domainAddress = new Address(
                address.getStreet(),
                address.getHouse(),
                address.getFlat(),
                address.getCity(),
                address.getRegion(),
                address.getTotalArea()
        );

        System.out.println("ADDRESS_DTO: " + address.toString());
        System.out.println("ADDRESS_DOMAIN: " + domainAddress.toString());


        user.setAddress(addressRepository.save(domainAddress));

        user.setCompany(companyRepository.findById(Long.parseLong(idCompany)).orElseThrow(() ->
                new EntityNotFoundException("Cant find company with ID: " + idCompany)));

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