package Dom.project.Infrastructure_layer.JpaRepo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import Dom.project.Infrastructure_layer.entity.CompanyJpaEntity;

public interface SpringDataCompanyRepository extends JpaRepository<CompanyJpaEntity, Long> {
    Optional<CompanyJpaEntity> findByEmail(String email);   
}