package Dom.project.Web_layer.api.dto;

import java.util.List;

public class CompanyProfileDto {
    private String companyName;
    private String address;
    private String inn;
    private String kpp;
    private String email;
    private List<UserProfileDto> clients;
    private List<WorkerDto> workers;

    public CompanyProfileDto() {}

    public CompanyProfileDto(String companyName, String address, String inn, String kpp, String email) {

        this.companyName = companyName;
        this.address = address;
        this.inn = inn;
        this.kpp = kpp;
        this.email = email;


    }

    // Геттеры и сеттеры

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getEmail() {return  email;}
    public void setEmail(String email) { this.email = email;}

    public String getInn() {return  inn;}
    public void setInn(String inn) { this.inn = inn;}

    public String getKpp() {return  kpp;}
    public void setKpp(String kpp) { this.kpp = kpp;}
}