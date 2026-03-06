package Dom.project.Infrastructure_layer.mappers;

import Dom.project.Domain_layer.model.User;
import Dom.project.Infrastructure_layer.entity.UserJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserJpaEntity toEntity(User user) {
        if (user==null){
            return null;
        }
        return null;
    }

    public User toDomain(UserJpaEntity saved) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toDomain'");
    }
}
