package Dom.project.Infrastructure_layer.mappers;

import Dom.project.Domain_layer.model.Address;
import Dom.project.Domain_layer.model.User;
import Dom.project.Infrastructure_layer.entity.UserJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

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
            user.getAddress().getId();
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
