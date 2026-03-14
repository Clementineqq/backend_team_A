package Dom.project.Infrastructure_layer.JpaRepo;

import Dom.project.Infrastructure_layer.entity.CompanyJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataCompanyRepository extends JpaRepository<CompanyJpaEntity, Long> {

}
