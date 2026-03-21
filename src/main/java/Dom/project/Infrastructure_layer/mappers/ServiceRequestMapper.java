package Dom.project.Infrastructure_layer.mappers;

import Dom.project.Domain_layer.model.ServiceRequest;
import Dom.project.Infrastructure_layer.entity.ServiceRequestJpaEntity;
import Dom.project.Infrastructure_layer.entity.UserJpaEntity;
import Dom.project.Infrastructure_layer.repoAdapters.ServiceRequestRepositoryAdapter;
import Dom.project.Infrastructure_layer.repoAdapters.UserRepositoryAdapter;
import jakarta.persistence.Column;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class ServiceRequestMapper {

    private UserRepositoryAdapter userRepositoryAdapter;
    private UserMapper userMapper;

    public ServiceRequestMapper(UserRepositoryAdapter userRepositoryAdapter, UserMapper userMapper) {
        this.userRepositoryAdapter = userRepositoryAdapter;
        this.userMapper = userMapper;
    }

    public ServiceRequestJpaEntity toEntity(ServiceRequest serviceRequest){
        ServiceRequestJpaEntity serviceRequestJpa = new ServiceRequestJpaEntity();

        serviceRequestJpa.setTitle(serviceRequest.getTitle());
        serviceRequestJpa.setDescription(serviceRequest.getDescription());
        serviceRequestJpa.setStatus(serviceRequest.getRequestStatus());
        serviceRequestJpa.setResolutionComment(serviceRequest.getSummary());
        serviceRequestJpa.setCompleted_at(serviceRequest.getCompletedAt());
        serviceRequestJpa.setDateCreate(serviceRequest.getCreatedAt());
        serviceRequestJpa.setDateUpdate(serviceRequest.getUpdatedAt());
        serviceRequestJpa.setID(serviceRequest.getId());
        serviceRequestJpa.setResolutionComment(null);

        if (serviceRequest.getCreator() != null){
            Long id = serviceRequest.getCreator().getId();
            UserJpaEntity creatorJpa = userRepositoryAdapter.findJpaById(id).
                    orElseThrow(() -> new EntityNotFoundException("Юзера (создателя) с таким ID не существует: " + id));

            serviceRequestJpa.setCreator(creatorJpa);
        } else {
            System.out.println("У таска нет создателя, ID таска" + serviceRequest.getId());
        }

        if (serviceRequest.getAssigner() != null){
            Long id = serviceRequest.getAssigner().getId();
            UserJpaEntity assigneeJpa = userRepositoryAdapter.findJpaById(id).
                    orElseThrow(() -> new EntityNotFoundException("Юзера (исполнителя) с таким ID не существует: " + id));

            serviceRequestJpa.setAssignee(assigneeJpa);
        } else {
            System.out.println("У таска нет исполнителя, ID таска" + serviceRequest.getId());
        }

        return serviceRequestJpa;
    }

    public ServiceRequest toDomain(ServiceRequestJpaEntity serviceRequestJpa){
        ServiceRequest serviceRequest = new ServiceRequest();

        serviceRequest.setId(serviceRequestJpa.getID());
        serviceRequest.setCreatedAt(serviceRequestJpa.getDateCreate());
        serviceRequest.setUpdatedAt(serviceRequestJpa.getDateUpdate());
        serviceRequest.setTitle(serviceRequestJpa.getTitle());
        serviceRequest.setDescription(serviceRequestJpa.getDescription());
        serviceRequest.setRequestStatus(serviceRequestJpa.getStatus());
        serviceRequest.setCreator(userMapper.toDomain(serviceRequestJpa.getCreator()));
        serviceRequest.setAssigner(userMapper.toDomain(serviceRequestJpa.getAssignee()));
        //TODO: добавить в domain resolution comment (после пуша ильи)
        //serviceRequest.setesolutionComment(null);
        serviceRequest.setCompletedAt(serviceRequestJpa.getCompleted_at());

        return serviceRequest;
    }
}
