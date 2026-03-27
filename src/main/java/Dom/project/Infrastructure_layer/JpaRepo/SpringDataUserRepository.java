package Dom.project.Infrastructure_layer.JpaRepo;

import java.util.List;
import java.util.Optional;

import Dom.project.Domain_layer.model.Counter;
import Dom.project.Domain_layer.model.User;
import Dom.project.Infrastructure_layer.entity.CounterJpaEntity;
import Dom.project.Infrastructure_layer.entity.ServiceRequestJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import Dom.project.Infrastructure_layer.entity.UserJpaEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SpringDataUserRepository extends JpaRepository<UserJpaEntity, Long> {

    Optional<UserJpaEntity> findByEmail(String email);
    Optional<UserJpaEntity> findByPhone(String phonenumber);

    @Query(value = "SELECT u FROM UserJpaEntity u WHERE u.company.id = ?1")
    List<UserJpaEntity> findByCompanyId(Long companyId);

    @Query(value = "SELECT u FROM UserJpaEntity u WHERE u.company.id = ?1 AND u.role = 'Worker'")
    List<UserJpaEntity> findAllWorkersByCompanyId(Long companyId);

}
