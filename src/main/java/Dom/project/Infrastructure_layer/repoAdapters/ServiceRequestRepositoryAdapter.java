package Dom.project.Infrastructure_layer.repoAdapters;

import Dom.project.Domain_layer.interfaces.repository.IServiceRequestRepository;
import Dom.project.Domain_layer.model.ServiceRequest;
import Dom.project.Infrastructure_layer.JpaRepo.SpringDataServiceRequestRepository;
import Dom.project.Infrastructure_layer.entity.AddressJpaEntity;
import Dom.project.Infrastructure_layer.entity.ServiceRequestJpaEntity;
import Dom.project.Infrastructure_layer.mappers.ServiceRequestMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ServiceRequestRepositoryAdapter implements IServiceRequestRepository {
    private ServiceRequestMapper _mapper;
    private SpringDataServiceRequestRepository _jpaRepository;

    public ServiceRequestRepositoryAdapter(ServiceRequestMapper mapper, SpringDataServiceRequestRepository jpaRepository) {
        _mapper = mapper;
        _jpaRepository = jpaRepository;
    }

    @Override
    public ServiceRequest save(ServiceRequest serviceRequest) {
        ServiceRequestJpaEntity entity = _mapper.toEntity(serviceRequest);
        ServiceRequestJpaEntity saved = _jpaRepository.save(entity);
        return _mapper.toDomain(saved);
    }

    @Override
    public Optional<ServiceRequest> findById(Long id) {
        return _jpaRepository.findById(id).map(_mapper::toDomain);
    }

    @Override
    public List<ServiceRequest> findByCreatorId(Long id) {
        List<ServiceRequestJpaEntity> entities = _jpaRepository.findByCreatorId(id);
        System.out.println(entities);
        System.out.println(id);

        return entities.stream()
                .map(_mapper::toDomain)
                .toList();
    }

    @Override
    public void delete(ServiceRequest serviceRequest) {
        System.out.println("DELETED SERVICE REQUEST ID: " + serviceRequest.getId());
        _jpaRepository.deleteById(serviceRequest.getId());
    }

    @Override
    public List<ServiceRequest> findByCompanyId(Long companyId) {
        List<ServiceRequestJpaEntity> entities = _jpaRepository.findByCompanyId(companyId);

        return entities.stream()
                .map(_mapper::toDomain)
                .toList();
    }

    @Override
    public List<ServiceRequest> findAllRequests() {
        List<ServiceRequestJpaEntity> entities = _jpaRepository.findAll();

        return entities.stream()
                .map(_mapper::toDomain)
                .toList();
    }

    public Optional<ServiceRequestJpaEntity> findJpaById(Long id) {
        return _jpaRepository.findById(id);
    }
}
