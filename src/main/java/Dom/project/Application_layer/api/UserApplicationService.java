package Dom.project.Application_layer.api;

import Dom.project.Domain_layer.enums.MeterType;
import Dom.project.Domain_layer.enums.UserRole;
import Dom.project.Domain_layer.exception.DomainException;
import Dom.project.Domain_layer.model.Address;
import Dom.project.Infrastructure_layer.repoAdapters.UserRepositoryAdapter;
import Dom.project.Web_layer.api.dto.AddressDto;
import Dom.project.Web_layer.api.dto.UserCountersDto;
import Dom.project.Web_layer.api.dto.UserProfileDto;
import Dom.project.Domain_layer.interfaces.repository.IUserRepository;
import Dom.project.Domain_layer.interfaces.repository.IServiceRequestRepository;
import Dom.project.Domain_layer.interfaces.repository.ICounterRepository;
import Dom.project.Domain_layer.model.User;
import Dom.project.Domain_layer.model.Counter;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserApplicationService {

    private final UserRepositoryAdapter userRepositoryAdapter;
    private final IUserRepository userRepository;
    private final IServiceRequestRepository serviceRequestRepository;
    private final ICounterRepository counterRepository;

    public UserApplicationService(UserRepositoryAdapter userRepositoryAdapter,
                                  IUserRepository userRepository,
                                  IServiceRequestRepository serviceRequestRepository,
                                  ICounterRepository counterRepository) {
        this.userRepositoryAdapter = userRepositoryAdapter;
        this.userRepository = userRepository;
        this.serviceRequestRepository = serviceRequestRepository;
        this.counterRepository = counterRepository;
    }

    public List<UserCountersDto> getUserCounters() {
        User currentUser = getCurrentUser();

        List<Counter> userCounters = counterRepository.findByUserId(currentUser.getId());

        return userCounters.stream()
                .map(this::convertToUserCountersDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void createCounter(UserCountersDto userCountersDto) {
        Counter counter = convertUserCounterToDomain(userCountersDto);
        counterRepository.save(counter);
    }

    @Transactional
    public void deleteCounter(Long id) throws EntityNotFoundException, AccessDeniedException {
        Counter counter = counterRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Counter not found"));

        User currUser = getCurrentUser();
        boolean isAdmin = checkAccess(currUser, List.of(UserRole.Admin));
        if (currUser.getId() != counter.getOwner().getId() && !isAdmin) {
            throw new AccessDeniedException("ACCESS DENIED");
        }

        counterRepository.delete(counter);
    }

    @Transactional
    public void deleteUser(Long id) throws AccessDeniedException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with ID " + id + "not found"));
        User currentUser = getCurrentUser();
        boolean isOwner = currentUser.getId() == id;
        boolean isAdmin = checkAccess(currentUser, List.of(UserRole.Admin));

        System.out.println(currentUser.getId());

        if (!isAdmin && !isOwner) {
            throw new AccessDeniedException("ACCESS DENIED");
        }

        userRepository.delete(user);
    }


    public UserProfileDto getCurrentUserProfile() {
        User currentUser = getCurrentUser();
        return convertToUserProfileDto(currentUser);
    }

    @Transactional
    public UserProfileDto updateUserProfile(UserProfileDto profileDto) {
        User currentUser = getCurrentUser();

        if (profileDto.getFirstName() != null) {
            currentUser.setName(profileDto.getFirstName());
        }

        if (profileDto.getLastName() != null) {
            currentUser.setLastName(profileDto.getLastName());
        }

        if (profileDto.getMiddleName() != null) {
            currentUser.setFatherName(profileDto.getMiddleName());
        }

        if (profileDto.getPhone() != null) {
            currentUser.setPhone_number(profileDto.getPhone());
        }

        if (profileDto.getEmail() != null) {
            currentUser.setEmail(profileDto.getEmail());
        }

        if (profileDto.getAddress() != null) {

            currentUser.updateAddress(convertToAddress(profileDto.getAddress()));
        }

        User updatedUser = userRepository.save(currentUser);
        return convertToUserProfileDto(updatedUser);
    }


    private Address convertToAddress(AddressDto address) {
        Address domainAddress = new Address();

        domainAddress.setCity(address.getCity());
        domainAddress.setFlat(address.getFlat());
        domainAddress.setHouse(address.getHouse());
        domainAddress.setRegion(address.getRegion());
        domainAddress.setStreet(address.getStreet());
        domainAddress.setTotalArea(address.getTotalArea());

        return domainAddress;
    }

    //Вспомогательный метод для получения текущего пользователя
    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getPrincipal().toString();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new DomainException("Текущий пользователь не найден"));
    }

    private UserProfileDto convertToUserProfileDto(User user) {
        UserProfileDto dto = new UserProfileDto();
        dto.setId(user.getId());
        dto.setFirstName(user.getName());
        dto.setLastName(user.getLastName());
        dto.setMiddleName(user.getFatherName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone_number());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        dto.setAddress(AddressDto.toDto(user.getAddress()));
        dto.setRole(user.getRole());

        return dto;
    }

    private UserCountersDto convertToUserCountersDto(Counter counter) {
        UserCountersDto dto = new UserCountersDto();
        dto.setId(counter.getId());
        dto.setName(counter.getName());
        dto.setValue(counter.getValue());
        dto.setCreatedAt(counter.getCreatedAt());
        dto.setUpdatedAt(counter.getUpdatedAt());
        dto.setIsApproved(counter.getIsApproved());
        dto.setOwner(convertToUserProfileDto(counter.getOwner()));
        return dto;
    }

    //todo: протестить
    private Counter convertUserCounterToDomain(UserCountersDto userCounterDto) {
        Counter counter = new Counter(
                userCounterDto.getName(),
                userCounterDto.getValue(),
                convertUserProfileToDomain(userCounterDto.getOwner())
        );

        return counter;
    }

    private User convertUserProfileToDomain(UserProfileDto userProfileDto) {
        User user = new User(
                userProfileDto.getPhone(),
                userProfileDto.getEmail(),
                userProfileDto.getPassword(),
                userProfileDto.getFirstName(),
                userProfileDto.getLastName(),
                userProfileDto.getRole()
                );
        return user;
    }

    public boolean checkAccess(User user, List<UserRole> requiredRoles) {
        return requiredRoles.contains(user.getRole());
    }

}