package Dom.project.Domain_layer.interfaces.repository;

import Dom.project.Domain_layer.model.Address;

import java.util.Optional;

public interface IAddressRepository {
    Address save(Address address);
    Optional<Address> findById(Long id);
}
