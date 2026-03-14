package Dom.project.Domain_layer.interfaces.repository;

import Dom.project.Domain_layer.model.Company;

import java.util.Optional;

public interface ICompanyRepository {
    Company save(Company company);
    Optional<Company> findById(Long id);
}
