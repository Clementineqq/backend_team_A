package Dom.project.Infrastructure_layer.entity;

import java.util.ArrayList;
import java.util.List;

import Dom.project.Domain_layer.enums.UserRole;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;


@Entity
@Table(name = "users")
public class UserJpaEntity extends Base_entity {
    
    @Column(nullable = false, length = 50)
    @NotBlank(message = "Имя обязательно")
    private String name;

    @Column(nullable = false, length = 50)
    @NotBlank(message = "Фамилия обязательна")
    private String surname;    

    @Column(unique = true, length = 20)
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Некорректный формат телефона")
    private String phone;

    @NotBlank
    @Column(unique = true, nullable = false)
    @Email(message = "Некорректный email")
    private String email;

    @OneToOne(mappedBy = "user") 
    private AddressJpaEntity address;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<MeterReadingJpaEntity> meterReadings = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ServiceRequestJpaEntity> serviceRequests = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AddressJpaEntity getAddress() {
        return address;
    }

    public void setAddress(AddressJpaEntity address) {
        this.address = address;
    }

    public List<MeterReadingJpaEntity> getMeterReadings() {
        return meterReadings;
    }

    public void setMeterReadings(List<MeterReadingJpaEntity> meterReadings) {
        this.meterReadings = meterReadings;
    }

    public List<ServiceRequestJpaEntity> getServiceRequests() {
        return serviceRequests;
    }

    public void setServiceRequests(List<ServiceRequestJpaEntity> serviceRequests) {
        this.serviceRequests = serviceRequests;
    }
}
