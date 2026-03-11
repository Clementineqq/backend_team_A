package Dom.project.Infrastructure_layer.mappers;

import Dom.project.Domain_layer.model.Address;
import Dom.project.Domain_layer.model.User;
import Dom.project.Infrastructure_layer.JpaRepo.SpringDataUserRepository;
import Dom.project.Infrastructure_layer.entity.AddressJpaEntity;
import Dom.project.Infrastructure_layer.entity.UserJpaEntity;
import Dom.project.Infrastructure_layer.repoAdapters.UserRepositoryAdapter;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Component
public class AddressMapper {
    private final UserMapper userMapper;
    private final UserRepositoryAdapter userRepositoryAdapter;

    public AddressMapper(UserMapper userMapper, UserRepositoryAdapter userRepositoryAdapter) {
        this.userMapper = userMapper;
        this.userRepositoryAdapter = userRepositoryAdapter;
    }

    public AddressJpaEntity toEntity(Address address){
        AddressJpaEntity addressJpa = new AddressJpaEntity();

        addressJpa.setRegion(address.getRegion());
        addressJpa.setCity(address.getCity());
        addressJpa.setHouse(address.getHouse());
        addressJpa.setFlat(address.getFlat());
        addressJpa.setTotalArea(address.getTotalArea());
        addressJpa.setDateCreate(address.getCreatedAt());
        addressJpa.setDateUpdate(address.getUpdatedAt());
        addressJpa.setID(addressJpa.getID());

        if (address.getUser_id() != null) {
            User user = userRepositoryAdapter.findById(address.getUser_id())
                    .orElseThrow(() -> new EntityNotFoundException("Нет юзера с таким id: " + address.getUser_id()));

            UserJpaEntity userJpa = new UserJpaEntity(UserMapper.toEntity(user));
            addressJpa.setUser(userJpa);
        }

        return addressJpa;
    }

    public Address toDomain(AddressJpaEntity addressJpa){

        Address address = new Address();

        address.setRegion(addressJpa.getRegion());
        address.setCity(addressJpa.getCity());
        address.setHouse(addressJpa.getHouse());
        address.setFlat(addressJpa.getFlat());
        address.setTotalArea(address.getTotalArea());
        address.setCreatedAt(address.getCreatedAt());
        address.setId(address.getId());
        address.setUser_id(addressJpa.getUser().getID());

        return null;
    };
}
