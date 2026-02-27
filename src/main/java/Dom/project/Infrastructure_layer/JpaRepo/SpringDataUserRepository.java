package Dom.project.Infrastructure_layer.JpaRepo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import Dom.project.Infrastructure_layer.entity.UserJpaEntity;

public interface SpringDataUserRepository extends JpaRepository<UserJpaEntity, Long> {

    Optional<UserJpaEntity> findByEmail(String email);
    Optional<UserJpaEntity> findByPhoneNumber(String phonenumber);
    
}
