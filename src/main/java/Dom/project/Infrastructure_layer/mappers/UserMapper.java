package Dom.project.Infrastructure_layer.mappers;

import Dom.project.Domain_layer.model.Address;
import Dom.project.Domain_layer.model.Counter;
import Dom.project.Domain_layer.model.User;
import Dom.project.Infrastructure_layer.entity.AddressJpaEntity;
import Dom.project.Infrastructure_layer.entity.CompanyJpaEntity;
import Dom.project.Infrastructure_layer.entity.CounterJpaEntity;
import Dom.project.Infrastructure_layer.entity.UserJpaEntity;
import Dom.project.Infrastructure_layer.repoAdapters.AddressRepositoryAdapter;
import Dom.project.Infrastructure_layer.repoAdapters.CompanyRepositoryAdapter;
import Dom.project.Infrastructure_layer.repoAdapters.CounterRepositoryAdapter;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {
    private final AddressRepositoryAdapter addressRepositoryAdapter;
    private final CompanyRepositoryAdapter companyRepositoryAdapter;

    private final CompanyMapper companyMapper;
    private final AddressMapper addressMapper;

    public UserMapper(AddressRepositoryAdapter addressRepositoryAdapter, CompanyRepositoryAdapter companyRepositoryAdapter, CompanyMapper companyMapper, AddressMapper addressMapper) {
        this.addressRepositoryAdapter = addressRepositoryAdapter;
        this.companyRepositoryAdapter = companyRepositoryAdapter;
        this.companyMapper = companyMapper;
        this.addressMapper = addressMapper;
    }


    public UserJpaEntity toEntity(User user) {
        if (user==null){
            return null;
        }

        UserJpaEntity userJpa = new UserJpaEntity();

        userJpa.setID(user.getId());
        userJpa.setDateCreate(user.getCreatedAt());
        userJpa.setPassword(user.getPassword());
        userJpa.setDateUpdate(user.getUpdatedAt());
        userJpa.setEmail(user.getEmail());
        userJpa.setPhone(user.getPhone_number());
        userJpa.setSurname(user.getLastName());
        userJpa.setName(user.getName());
        userJpa.setFather_name(user.getFatherName());
        userJpa.setRole(user.getRole());

        // установка адреса
        if (user.getAddress() != null){
            AddressJpaEntity address = addressRepositoryAdapter.findJpaById(user.getAddress().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Нет юзера с таким id: " + user.getAddress().getId()));

            userJpa.setAddress(address);
        }

        if (user.getCompany() != null){
            CompanyJpaEntity companyJpa = companyRepositoryAdapter.findJpaById(user.getCompany().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Нет компании с таким id: " + user.getCompany().getId()));

            userJpa.setCompany(companyJpa);
        }

        return userJpa;
    }

    public User toDomain(UserJpaEntity userJpa) {
        if (userJpa == null){
            return null;
        }

        User user = new User();

        user.setId(userJpa.getID());
        user.setCreatedAt(userJpa.getDateCreate());
        user.setUpdatedAt(userJpa.getDateUpdate());

        user.setName(userJpa.getName());
        user.setPassword(userJpa.getPassword());
        user.setEmail(userJpa.getEmail());
        user.setFatherName(userJpa.getFather_name());
        user.setLastName(userJpa.getSurname());
        user.setPhone_number(userJpa.getPhone());
        user.setCompany(companyMapper.toDomain(userJpa.getCompany()));
        user.setAddress(addressMapper.toDomain(userJpa.getAddress()));
        user.setRole(userJpa.getRole());

        return user;
    }
}
