package Dom.project.Infrastructure_layer.mappers;

import org.springframework.stereotype.Component;

import Dom.project.Domain_layer.model.Company;
import Dom.project.Infrastructure_layer.entity.AddressJpaEntity;
import Dom.project.Infrastructure_layer.entity.CompanyJpaEntity;
import Dom.project.Infrastructure_layer.repoAdapters.AddressRepositoryAdapter;
import jakarta.persistence.EntityNotFoundException;

@Component
public class CompanyMapper {
    private final AddressRepositoryAdapter addressRepositoryAdapter;
    private final AddressMapper addressMapper;

    public CompanyMapper(AddressRepositoryAdapter addressRepositoryAdapter, AddressMapper addressMapper) {
        this.addressRepositoryAdapter = addressRepositoryAdapter;
        this.addressMapper = addressMapper;
    }

    public CompanyJpaEntity toEntity(Company company){
        if (company == null){
            System.out.println("company = null");
            return null;
        }

        CompanyJpaEntity companyJpa = new CompanyJpaEntity();

        companyJpa.setName(company.getName());
        companyJpa.setEmail(company.getEmail());
        companyJpa.setInn(company.getInn());
        companyJpa.setKpp(company.getKpp());
        companyJpa.setID(company.getId());
        companyJpa.setDateCreate(company.getCreatedAt());
        companyJpa.setDateUpdate(company.getUpdatedAt());
        companyJpa.setDescription(company.getDescription());

        if (company.getLegalAddress() != null){
            Long address_id = company.getLegalAddress().getId();
            AddressJpaEntity addressJpa = addressRepositoryAdapter.findJpaById(address_id)
                    .orElseThrow(() -> new EntityNotFoundException("Нет адреса с таким id" + address_id));

            companyJpa.setLegal_address(addressJpa);
        }

        return companyJpa;
    }

    public Company toDomain(CompanyJpaEntity companyJpa){
        if (companyJpa == null){
            System.out.println("companyJpa = null");
            return null;
        }

        Company company = new Company();

        company.setName(companyJpa.getName());
        company.setEmail(companyJpa.getEmail());
        company.setKpp(companyJpa.getKpp());
        company.setInn(companyJpa.getInn());
        company.setCreatedAt(companyJpa.getDateCreate());
        company.setId(companyJpa.getID());
        company.setDescription(companyJpa.getDescription());
        company.setLegalAddress(addressMapper.toDomain(
                companyJpa.getLegal_address()));

        return company;

    }
}
