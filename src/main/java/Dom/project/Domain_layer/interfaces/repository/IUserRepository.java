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

    // TODO: *
     void delete(User user);

    // TODO: *
     List<User> findAllWithCompany();
}
