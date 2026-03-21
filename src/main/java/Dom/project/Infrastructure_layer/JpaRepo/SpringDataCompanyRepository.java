package Dom.project.Infrastructure_layer.JpaRepo;

import java.util.List;
import java.util.Optional;

import Dom.project.Domain_layer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import Dom.project.Infrastructure_layer.entity.CompanyJpaEntity;
import org.springframework.data.jpa.repository.Query;

public interface SpringDataCompanyRepository extends JpaRepository<CompanyJpaEntity, Long> {
    Optional<CompanyJpaEntity> findByEmail(String email);
}