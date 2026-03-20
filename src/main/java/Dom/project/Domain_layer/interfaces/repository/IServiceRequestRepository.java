package Dom.project.Domain_layer.interfaces.repository;

import Dom.project.Domain_layer.model.ServiceRequest;

import java.util.List;
import java.util.Optional;

public interface IServiceRequestRepository {
    ServiceRequest save(ServiceRequest serviceRequest);
    Optional<ServiceRequest> findById(Long id);

    // TODO: Доделать поиск всех тасков пользователя
    List<ServiceRequest> findByCreatorId(Long id);

    void delete(ServiceRequest serviceRequest);
}
