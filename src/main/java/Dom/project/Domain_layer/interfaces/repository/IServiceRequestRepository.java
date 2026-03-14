package Dom.project.Domain_layer.interfaces.repository;

import Dom.project.Domain_layer.model.ServiceRequest;

import java.util.Optional;

public interface IServiceRequestRepository {
    ServiceRequest save(ServiceRequest serviceRequest);
    Optional<ServiceRequest> findById(Long id);
}
