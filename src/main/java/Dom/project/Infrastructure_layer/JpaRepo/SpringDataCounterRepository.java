package Dom.project.Infrastructure_layer.JpaRepo;

import Dom.project.Infrastructure_layer.entity.CounterJpaEntity;
import Dom.project.Infrastructure_layer.entity.ServiceRequestJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SpringDataCounterRepository extends JpaRepository<CounterJpaEntity, Long> {

    @Query(value = "SELECT c FROM CounterJpaEntity c WHERE c.user.id = ?1")
    List<CounterJpaEntity> findByUserId(Long userId);

}
