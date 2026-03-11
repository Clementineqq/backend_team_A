package Dom.project.Infrastructure_layer.mappers;

import Dom.project.Domain_layer.model.Address;
import Dom.project.Domain_layer.model.User;
import Dom.project.Infrastructure_layer.entity.AddressJpaEntity;
import Dom.project.Infrastructure_layer.entity.UserJpaEntity;
import Dom.project.Infrastructure_layer.repoAdapters.AddressRepositoryAdapter;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    private final AddressRepositoryAdapter addressRepositoryAdapter;

    public UserMapper(AddressRepositoryAdapter addressRepositoryAdapter) {
        this.addressRepositoryAdapter = addressRepositoryAdapter;
    }


    public UserJpaEntity toEntity(User user) {
        if (user==null){
            return null;
        }

        UserJpaEntity userJpa = new UserJpaEntity();

        userJpa.setID(user.getId());
        userJpa.setDateCreate(user.getCreatedAt());
        userJpa.setDateUpdate(user.getUpdatedAt());
        userJpa.setEmail(user.getEmail());
        userJpa.setPhone(user.getPhone_number());
        userJpa.setSurname(user.getLastName());
        userJpa.setName(user.getName());
        userJpa.setFather_name(userJpa.getFather_name());

        // TODO: сначала нужно сделать repoAdapters
        // установка адреса
        if (user.getAddress() != null){
            AddressJpaEntity address = addressRepositoryAdapter.findJpaById(user.getAddress().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Нет юзера с таким id: " + user.getAddress().getId()));

            userJpa.setAddress(address);
        }

        // установка счетчиков

        // установка созданных тасков

        return null;
    }

    public User toDomain(UserJpaEntity saved) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toDomain'");
    }
}
