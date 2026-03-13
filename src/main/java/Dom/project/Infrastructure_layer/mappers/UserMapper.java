package Dom.project.Infrastructure_layer.mappers;

import org.springframework.stereotype.Component;

import Dom.project.Domain_layer.model.User;
import Dom.project.Infrastructure_layer.entity.AddressJpaEntity;
import Dom.project.Infrastructure_layer.entity.UserJpaEntity;
import Dom.project.Infrastructure_layer.repoAdapters.AddressRepositoryAdapter;
import jakarta.persistence.EntityNotFoundException;

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

         System.out.println("========== toEntity ==========");
        System.out.println("Email: " + user.getEmail());
        System.out.println("Password from user object: '" + user.getPassword() + "'");
        System.out.println("Password length: " + (user.getPassword() != null ? user.getPassword().length() : 0));
        
        UserJpaEntity userJpa = new UserJpaEntity();

        userJpa.setID(user.getId());
        userJpa.setDateCreate(user.getCreatedAt());
        userJpa.setDateUpdate(user.getUpdatedAt());
        userJpa.setEmail(user.getEmail());
        userJpa.setPhone(user.getPhone_number());
        userJpa.setSurname(user.getLastName());
        userJpa.setName(user.getName());
        userJpa.setFather_name(userJpa.getFather_name());

        userJpa.setPassword(user.getPassword()); // добавил
        // TODO: сначала нужно сделать repoAdapters
        // установка адреса
        if (user.getAddress() != null){
            AddressJpaEntity address = addressRepositoryAdapter.findJpaById(user.getAddress().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Нет юзера с таким id: " + user.getAddress().getId()));

            userJpa.setAddress(address);
        }

        // установка счетчиков

        //
         System.out.println("Password set in entity: '" + userJpa.getPassword() + "'");
     System.out.println("==============================");
       // return null; <- это было изначально, но выдавала  [Request processing failed: org.springframework.dao.InvalidDataAccessApiUsageException: Entity must not be null] with root cause
     return userJpa; // поэтому это возвращаю
    }

    public User toDomain(UserJpaEntity entity) {
            if (entity == null) {
        return null;
    }
    
    System.out.println("========== toDomain ==========");
    System.out.println("Email from entity: " + entity.getEmail());
    System.out.println("Password from entity: '" + entity.getPassword() + "'");
    



    User user = new User();
    user.setId(entity.getID());
    user.setCreatedAt(entity.getDateCreate());
    user.setUpdatedAt(entity.getDateUpdate());
    user.setEmail(entity.getEmail());
    user.setPhone_number(entity.getPhone());   
    user.setLastName(entity.getSurname());
    user.setName(entity.getName());
    user.setFatherName(entity.getFather_name());
    
    user.setPassword(entity.getPassword());
    
     System.out.println("Password set in user: '" + user.getPassword() + "'");
    System.out.println("==============================");
    
    return user;
        //ну тут я еблан, что поздно засел, поэтому уже я решил сделать кое-как
        // TODO Auto-generated method stub
       // throw new UnsupportedOperationException("Unimplemented method 'toDomain'");
    }
}
