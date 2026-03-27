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
    private Utils util;

    public UserApplicationService(UserRepositoryAdapter userRepositoryAdapter,
                                  IUserRepository userRepository,
                                  IServiceRequestRepository serviceRequestRepository,
                                  ICounterRepository counterRepository, Utils util) {
        this.userRepositoryAdapter = userRepositoryAdapter;
        this.userRepository = userRepository;
        this.serviceRequestRepository = serviceRequestRepository;
        this.counterRepository = counterRepository;
        this.util = util;
    }

    public List<UserCountersDto> getUserCounters() {
        User currentUser = util.getCurrentUser();

        List<Counter> userCounters = counterRepository.findByUserId(currentUser.getId());

        return userCounters.stream()
                .map(util::convertToUserCountersDto)
                .collect(Collectors.toList());
    }

    public UserCountersDto getCounter(Long counterId){
        //todo: *
        return null;
    }

    @Transactional
    public void createCounter(UserCountersDto userCountersDto) {
        Counter counter = util.convertUserCounterToDomain(userCountersDto);
        counterRepository.save(counter);
    }

    @Transactional
    public void deleteCounter(Long id) throws EntityNotFoundException, AccessDeniedException {
        Counter counter = counterRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Counter not found"));

        User currUser = util.getCurrentUser();
        boolean isAdmin = util.checkAccess(currUser, List.of(UserRole.Admin));
        if (currUser.getId() != counter.getOwner().getId() && !isAdmin) {
            throw new AccessDeniedException("ACCESS DENIED");
        }

        counterRepository.delete(counter);
    }

    @Transactional
    public void deleteUser(Long id) throws AccessDeniedException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with ID " + id + "not found"));
        User currentUser = util.getCurrentUser();
        boolean isOwner = currentUser.getId() == id;
        boolean isAdmin = util.checkAccess(currentUser, List.of(UserRole.Admin));

        System.out.println(currentUser.getId());

        if (!isAdmin && !isOwner) {
            throw new AccessDeniedException("ACCESS DENIED");
        }

        userRepository.delete(user);
    }


    public UserProfileDto getCurrentUserProfile() {
        User currentUser = util.getCurrentUser();
        return util.convertToUserProfileDto(currentUser);
    }

    @Transactional
    public UserProfileDto updateUserProfile(UserProfileDto profileDto) {
        User currentUser = util.getCurrentUser();

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

            currentUser.updateAddress(util.convertToAddress(profileDto.getAddress()));
        }

        User updatedUser = userRepository.save(currentUser);
        return util.convertToUserProfileDto(updatedUser);
    }



}