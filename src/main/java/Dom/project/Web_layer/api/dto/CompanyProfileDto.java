package Dom.project.Web_layer.api.dto;

import java.util.List;

public class CompanyProfileDto {
    Long id;
    private String companyName;
    private String inn;
    private String kpp;
    private String email;
    private String descriprion;
    private AddressDto address;
    private List<UserProfileDto> members;
    private List<WorkerDto> workers;
    private List<ServiceRequestDto> companyRequests;
    private List<UserProfileDto> companyOwner;

    public CompanyProfileDto() {}

    public CompanyProfileDto(Long id, String companyName, AddressDto address, String inn, String kpp, String email, String descriprion) {
        this.id = id;
        this.companyName = companyName;
        this.address = address;
        this.inn = inn;
        this.kpp = kpp;
        this.email = email;
        this.descriprion = descriprion;
    }

    // Геттеры и сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public AddressDto getAddress() { return address; }
    public void setAddress(AddressDto address) { this.address = address; }

    public String getInn() { return inn; }
    public void setInn(String inn) { this.inn = inn; }

    public String getKpp() { return kpp; }
    public void setKpp(String kpp) { this.kpp = kpp; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public List<UserProfileDto> getMembers() { return members; }
    public void setMembers(List<UserProfileDto> members) { this.members = members; }

    public List<WorkerDto> getWorkers() { return workers; }
    public void setWorkers(List<WorkerDto> workers) { this.workers = workers; }

    public List<ServiceRequestDto> getCompanyRequests() { return companyRequests; }
    public void setCompanyRequests(List<ServiceRequestDto> companyRequests) { this.companyRequests = companyRequests; }

    public List<UserProfileDto> getCompanyOwner() { return companyOwner; }
    public void setCompanyOwner(List<UserProfileDto> companyOwner) {this.companyOwner = companyOwner;}

    public String getDescriprion() { return descriprion; }

    public void setDescriprion(String descriprion) { this.descriprion = descriprion; }
}