package Dom.project.Application_layer.api;

import Dom.project.Domain_layer.exception.DomainException;
import Dom.project.Domain_layer.model.Address;
import Dom.project.Domain_layer.model.Counter;
import Dom.project.Domain_layer.model.User;
import Dom.project.Infrastructure_layer.repoAdapters.UserRepositoryAdapter;
import Dom.project.Web_layer.api.dto.AddressDto;
import Dom.project.Web_layer.api.dto.UserCountersDto;
import Dom.project.Web_layer.api.dto.UserProfileDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class Utils {
    UserRepositoryAdapter userRepository;

    public Utils(UserRepositoryAdapter userRepository){
        this.userRepository = userRepository;
    }

    // converts
    public Address convertToAddress(AddressDto address) {
        Address domainAddress = new Address();

        domainAddress.setCity(address.getCity());
        domainAddress.setFlat(address.getFlat());
        domainAddress.setHouse(address.getHouse());
        domainAddress.setRegion(address.getRegion());
        domainAddress.setStreet(address.getStreet());
        domainAddress.setTotalArea(address.getTotalArea());

        return domainAddress;
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



    // other

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getPrincipal().toString();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new DomainException("Текущий пользователь не найден"));
    }


}
