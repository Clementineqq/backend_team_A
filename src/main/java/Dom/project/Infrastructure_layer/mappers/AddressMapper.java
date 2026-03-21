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
    public AddressJpaEntity toEntity(Address address){
        AddressJpaEntity addressJpa = new AddressJpaEntity();

        addressJpa.setRegion(address.getRegion());
        addressJpa.setCity(address.getCity());
        addressJpa.setHouse(address.getHouse());
        addressJpa.setStreet(address.getStreet());
        addressJpa.setFlat(address.getFlat());
        addressJpa.setTotalArea(address.getTotalArea());
        addressJpa.setDateCreate(address.getCreatedAt());
        addressJpa.setDateUpdate(address.getUpdatedAt());
        addressJpa.setID(address.getId());

        return addressJpa;
    }

    public Address toDomain(AddressJpaEntity addressJpa){

        Address address = new Address();

        address.setRegion(addressJpa.getRegion());
        address.setCity(addressJpa.getCity());
        address.setHouse(addressJpa.getHouse());
        address.setStreet(addressJpa.getStreet());
        address.setFlat(addressJpa.getFlat());
        address.setTotalArea(addressJpa.getTotalArea());
        address.setCreatedAt(addressJpa.getDateCreate());
        address.setId(addressJpa.getID());

        return address;
    };
}
