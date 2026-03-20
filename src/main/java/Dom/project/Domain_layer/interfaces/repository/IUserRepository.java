package Dom.project.Domain_layer.interfaces.repository;

import java.util.List;
import java.util.Optional;

import Dom.project.Domain_layer.model.User;

public interface IUserRepository {
     User save(User user);
     Optional<User> findById(Long id);
     Optional<User> findByEmail(String email);
     Optional<User> findByPhone(String phonenumber);

     List<User> findByCompanyId(Long companyId);

     void delete(User user);

     List<User> findAllWithCompany();
}
