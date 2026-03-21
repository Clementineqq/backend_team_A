package Dom.project.Infrastructure_layer.repoAdapters;

import Dom.project.Domain_layer.interfaces.repository.ICounterRepository;
import Dom.project.Domain_layer.model.Counter;
import Dom.project.Infrastructure_layer.JpaRepo.SpringDataCounterRepository;
import Dom.project.Infrastructure_layer.entity.CounterJpaEntity;
import Dom.project.Infrastructure_layer.mappers.CounterMapper;
import org.springframework.stereotype.Component;

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
    public Object findByUserId(Long id) {
        return _jpaRepository.findById(id).map(_mapper::toDomain);
    }

    public Optional<CounterJpaEntity> findJpaById(Long id) {
        return _jpaRepository.findById(id);
    }
}
