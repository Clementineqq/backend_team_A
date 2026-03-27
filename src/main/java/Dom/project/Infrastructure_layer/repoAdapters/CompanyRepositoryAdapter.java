package Dom.project.Infrastructure_layer.repoAdapters;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import Dom.project.Domain_layer.interfaces.repository.ICompanyRepository;
import Dom.project.Domain_layer.model.Company;
import Dom.project.Infrastructure_layer.JpaRepo.SpringDataCompanyRepository;
import Dom.project.Infrastructure_layer.entity.CompanyJpaEntity;
import Dom.project.Infrastructure_layer.mappers.CompanyMapper;

@Component
public class CompanyRepositoryAdapter implements ICompanyRepository {
    private CompanyMapper _mapper;
    private SpringDataCompanyRepository _jpaRepository;
    private final PasswordEncoder passwordEncoder;

    public CompanyRepositoryAdapter(CompanyMapper mapper, SpringDataCompanyRepository jpaRepository) {
        _mapper = mapper;
        _jpaRepository = jpaRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }


    @Override
    public Company save(Company company) {
        CompanyJpaEntity entity = _mapper.toEntity(company);
        CompanyJpaEntity saved = _jpaRepository.save(entity);

        return _mapper.toDomain(saved);
    }

    @Override
    public Optional<Company> findById(Long id) {
        return _jpaRepository.findById(id).map(_mapper::toDomain);
    }
    @Override
    public Optional<Company> findByEmail(String email) {
        return _jpaRepository.findByEmail(email).map(_mapper::toDomain);
    }

    public Optional<CompanyJpaEntity> findJpaById(Long id) {
        return _jpaRepository.findById(id);
    }

    


}
