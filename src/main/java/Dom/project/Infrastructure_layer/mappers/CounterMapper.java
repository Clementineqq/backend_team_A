package Dom.project.Infrastructure_layer.mappers;

import Dom.project.Domain_layer.model.Counter;
import Dom.project.Infrastructure_layer.entity.CounterJpaEntity;
import Dom.project.Infrastructure_layer.entity.UserJpaEntity;
import Dom.project.Infrastructure_layer.repoAdapters.UserRepositoryAdapter;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CounterMapper {
    private final UserRepositoryAdapter userRepositoryAdapter;
    private final UserMapper userMapper;

    public CounterMapper(UserRepositoryAdapter userRepositoryAdapter, UserMapper userMapper) {
        this.userRepositoryAdapter = userRepositoryAdapter;
        this.userMapper = userMapper;
    }

    public CounterJpaEntity toEntity(Counter counter){
        if (counter == null){
            return null;
        }

        CounterJpaEntity counterJpa = new CounterJpaEntity();

        counterJpa.setType(counter.getName());
        counterJpa.setIsApproved(counter.getIsApproved());
        counterJpa.setValue(counter.getValue());
        counterJpa.setID(counter.getId());
        counterJpa.setDateCreate(counter.getCreatedAt());
        counterJpa.setDateUpdate(counter.getUpdatedAt());

        if (counter.getOwner().getId() != null){
            UserJpaEntity owner = userRepositoryAdapter.findJpaById(counter.getOwner().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Такого пользователя нет"));

            counterJpa.setUser(owner);
        }

        return counterJpa;
    }

    public Counter toDomain(CounterJpaEntity counterJpa){
        if (counterJpa == null){
            return null;
        }

        Counter counter = new Counter();

        counter.setId(counterJpa.getID());
        counter.setName(counterJpa.getType());
        counter.setValueFromDatabase(counterJpa.getValue());
        counter.setCreatedAt(counterJpa.getDateCreate());
        counter.setUpdatedAt(counterJpa.getDateUpdate());
        counter.setIsApproved(counterJpa.isApproved());
        counter.setOwner(userMapper.toDomain(counterJpa.getUser()));

        return counter;
    }
}
