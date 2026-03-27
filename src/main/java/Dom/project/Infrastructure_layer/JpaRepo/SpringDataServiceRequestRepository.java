package Dom.project.Infrastructure_layer.JpaRepo;

import Dom.project.Domain_layer.model.ServiceRequest;
import Dom.project.Infrastructure_layer.entity.ServiceRequestJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SpringDataServiceRequestRepository extends JpaRepository<ServiceRequestJpaEntity, Long> {

    @Query(value = "SELECT s FROM ServiceRequestJpaEntity s WHERE s.creator.id = ?1")
    List<ServiceRequestJpaEntity> findByCreatorId(Long creatorId);

    @Query(value = "SELECT s FROM ServiceRequestJpaEntity s WHERE s.creator.company.id= ?1")
    List<ServiceRequestJpaEntity> findByCompanyId(Long companyId);
}
