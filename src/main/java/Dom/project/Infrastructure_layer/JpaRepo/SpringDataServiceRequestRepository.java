package Dom.project.Infrastructure_layer.JpaRepo;

import Dom.project.Infrastructure_layer.entity.ServiceRequestJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataServiceRequestRepository extends JpaRepository<ServiceRequestJpaEntity, Long> {

}
