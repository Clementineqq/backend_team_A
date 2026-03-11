package Dom.project.Infrastructure_layer.repoAdapters;

import Dom.project.Domain_layer.interfaces.repository.IAddressRepository;
import Dom.project.Domain_layer.model.Address;
import Dom.project.Infrastructure_layer.JpaRepo.SpringDataAddressRepository;
import Dom.project.Infrastructure_layer.JpaRepo.SpringDataUserRepository;
import Dom.project.Infrastructure_layer.entity.AddressJpaEntity;
import Dom.project.Infrastructure_layer.mappers.AddressMapper;
import Dom.project.Infrastructure_layer.mappers.UserMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AddressRepositoryAdapter implements IAddressRepository {
    private AddressMapper _mapper;
    private SpringDataAddressRepository _jpaRepository;

    public AddressRepositoryAdapter(SpringDataAddressRepository jpaRepository, AddressMapper mapper) {
        _jpaRepository= jpaRepository;
        _mapper = mapper;
    }

    @Override
    public Address save(Address address) {
        return null;
    }

    @Override
    public Optional<Address> findById(Long id) {
        return _jpaRepository.findById(id).map(_mapper::toDomain);
    }

    public Optional<AddressJpaEntity> findJpaById(Long id) {
        return _jpaRepository.findById(id);
    }
}
