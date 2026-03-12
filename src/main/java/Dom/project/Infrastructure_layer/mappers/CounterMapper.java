package Dom.project.Infrastructure_layer.mappers;

import Dom.project.Domain_layer.model.Counter;
import Dom.project.Infrastructure_layer.entity.CounterJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class CounterMapper {

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


        return counterJpa;
    }

    public Counter toDomain(CounterJpaEntity counterJpa){
        if (counterJpa == null){
            return null;
        }

        Counter counter = new Counter();

        counter.setId(counterJpa.getID());
        counter.setName(counterJpa.getType());
        counter.setValue(counter.getValue());
        counter.setCreatedAt(counterJpa.getDateCreate());
        counter.setIsApproved(counterJpa.isApproved());

        return counter;
    }
}
