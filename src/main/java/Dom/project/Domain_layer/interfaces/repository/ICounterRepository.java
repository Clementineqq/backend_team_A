package Dom.project.Domain_layer.interfaces.repository;

import Dom.project.Domain_layer.model.Counter;

import java.util.Optional;

public interface ICounterRepository {
    Counter save(Counter counter);
    Optional<Counter> findById(Long id);
}
