package Dom.project.Infrastructure_layer.repoAdapters;

import java.util.Optional;

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
         UserJpaEntity entity = _mapper.toEntity(user);
        UserJpaEntity saved = _jpaRepository.save(entity);
        return _mapper.toDomain(saved);
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
    public Optional<User> findByPhoneNumber(String phonenumber) {
        return _jpaRepository.findByEmail(phonenumber).map(_mapper::toDomain);
    }

    

}
