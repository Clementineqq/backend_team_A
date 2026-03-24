package Dom.project.Domain_layer.interfaces.repository;

import Dom.project.Domain_layer.model.ServiceRequest;

import java.util.List;
import java.util.Optional;

public interface IServiceRequestRepository {
    ServiceRequest save(ServiceRequest serviceRequest);
    Optional<ServiceRequest> findById(Long id);

    // TODO: проверить
    List<ServiceRequest> findByCreatorId(Long id);

    // done
    void delete(ServiceRequest serviceRequest);

    // done
    List<ServiceRequest> findByCompanyId(Long companyId);
}
