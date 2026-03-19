package Dom.project.Infrastructure_layer.repoAdapters;

import java.util.Optional;

import Dom.project.Infrastructure_layer.entity.AddressJpaEntity;
import org.springframework.stereotype.Component;

import Dom.project.Domain_layer.interfaces.repository.IUserRepository;
import Dom.project.Domain_layer.model.User;
import Dom.project.Infrastructure_layer.JpaRepo.SpringDataUserRepository;
import Dom.project.Infrastructure_layer.entity.UserJpaEntity;
import Dom.project.Infrastructure_layer.mappers.UserMapper;

@Component
public class UserRepositoryAdapter implements IUserRepository { 
    private final SpringDataUserRepository _jpaRepository; 
    private final UserMapper _mapper;

    public UserRepositoryAdapter(SpringDataUserRepository jpaRepository, UserMapper mapper) {
        _jpaRepository= jpaRepository;
        _mapper = mapper;
    }

    @Override
    public User save(User user) {
         System.out.println("========== UserRepositoryAdapter.save ==========");
        System.out.println("1. Received user with email: " + user.getEmail());
        System.out.println("2. User password: '" + user.getPassword() + "'");

        UserJpaEntity entity = _mapper.toEntity(user);
        System.out.println("3. After toEntity - entity password: '" + (entity != null ? entity.getPassword() : "NULL ENTITY") + "'");

        UserJpaEntity saved = _jpaRepository.save(entity);
        System.out.println("4. After DB save - saved password: '" + saved.getPassword() + "'");
        User result = _mapper.toDomain(saved);
        System.out.println("5. Final result password: '" + result.getPassword() + "'");
        System.out.println("==================================================");

        return result;
}
   
    @Override
    public Optional<User> findById(Long id) {
        return _jpaRepository.findById(id).map(_mapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return _jpaRepository.findByEmail(email).map(_mapper::toDomain);
    }

    @Override
    public Optional<User> findByPhone(String phonenumber) {
        return _jpaRepository.findByPhone(phonenumber).map(_mapper::toDomain);
    }

    public Optional<UserJpaEntity> findJpaById(Long id) {
        return _jpaRepository.findById(id);
    }
}
