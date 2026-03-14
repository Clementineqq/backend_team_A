package Dom.project.Infrastructure_layer.JpaRepo;

import Dom.project.Infrastructure_layer.entity.CounterJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SpringDataCounterRepository extends JpaRepository<CounterJpaEntity, Long> {
}
