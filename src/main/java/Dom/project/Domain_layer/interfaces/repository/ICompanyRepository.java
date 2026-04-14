
package Dom.project.Domain_layer.interfaces.repository;

import java.util.List;
import java.util.Optional;

import Dom.project.Domain_layer.model.Company;

public interface ICompanyRepository {
    Company save(Company company);
    Optional<Company> findById(Long id);
    Optional<Company> findByEmail(String email);
    List<Company> findAll();
}