package Dom.project.Domain_layer.interfaces.repository;

import Dom.project.Domain_layer.model.Counter;
import Dom.project.Domain_layer.model.User;
import Dom.project.Infrastructure_layer.entity.UserJpaEntity;

import java.util.List;
import java.util.Optional;

public interface ICounterRepository {
    Counter save(Counter counter);
    Optional<Counter> findById(Long id);

    //done
    List<Counter> findByUserId(Long id);
    
    //done
    void delete(Counter counter);
}
