package Dom.project.Infrastructure_layer.repoAdapters;

import Dom.project.Domain_layer.interfaces.repository.ICounterRepository;
import Dom.project.Domain_layer.model.Counter;
import Dom.project.Domain_layer.model.User;
import Dom.project.Infrastructure_layer.JpaRepo.SpringDataCounterRepository;
import Dom.project.Infrastructure_layer.entity.CounterJpaEntity;
import Dom.project.Infrastructure_layer.entity.ServiceRequestJpaEntity;
import Dom.project.Infrastructure_layer.mappers.CounterMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CounterRepositoryAdapter implements ICounterRepository {
    private CounterMapper _mapper;
    private SpringDataCounterRepository _jpaRepository;

    public CounterRepositoryAdapter(CounterMapper mapper, SpringDataCounterRepository jpaRepository) {
        _mapper = mapper;
        _jpaRepository = jpaRepository;
    }

    @Override
    public Counter save(Counter counter) {
        CounterJpaEntity entity = _mapper.toEntity(counter);
        CounterJpaEntity saved = _jpaRepository.save(entity);
        return _mapper.toDomain(saved);
    }

    @Override
    public Optional<Counter> findById(Long id) {
        return _jpaRepository.findById(id).map(_mapper::toDomain);
    }

    @Override
    public List<Counter> findByUserId(Long id) {
        List<CounterJpaEntity> entities = _jpaRepository.findByUserId(id);

        return entities.stream()
                .map(_mapper::toDomain)
                .toList();
    }

    @Override
    public void delete(Counter counter){
        System.out.println("DELETED COUNTER ID: " + counter.getId());
        _jpaRepository.deleteById(counter.getId());
    }

    public Optional<CounterJpaEntity> findJpaById(Long id) {
        return _jpaRepository.findById(id);
    }
}
