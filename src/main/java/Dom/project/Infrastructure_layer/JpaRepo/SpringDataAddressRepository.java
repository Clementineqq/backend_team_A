package Dom.project.Infrastructure_layer.JpaRepo;

import Dom.project.Infrastructure_layer.entity.AddressJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataAddressRepository extends JpaRepository<AddressJpaEntity, Long> {

}
