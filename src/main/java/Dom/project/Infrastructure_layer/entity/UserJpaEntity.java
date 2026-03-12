package Dom.project.Infrastructure_layer.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;


@Entity
@Table(name = "users")
public class UserJpaEntity extends Base_entity {
    
    @Column(nullable = false, length = 50)
    @NotBlank(message = "Имя обязательно")
    private String name;

    @Column(nullable = false, length = 50, name="last_name")
    @NotBlank(message = "Фамилия обязательна")
    private String surname;

    @Column(name = "fathers_name")
    private String father_name;

    @Column(unique = true, length = 20)
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Некорректный формат телефона")
    private String phone;

    @NotBlank
    @Column(unique = true, nullable = false)
    @Email(message = "Некорректный email")
    private String email;

    @OneToOne()
    @JoinColumn(name="id_adress")
    private AddressJpaEntity address;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<CounterJpaEntity> counters = new ArrayList<>();

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

    public List<CounterJpaEntity> getCounters() {
        return counters;
    }

    public void setCounters(List<CounterJpaEntity> meterReadings) {
        this.counters = meterReadings;
    }

    public List<ServiceRequestJpaEntity> getServiceRequests() {
        return serviceRequests;
    }

    public void setServiceRequests(List<ServiceRequestJpaEntity> serviceRequests) {
        this.serviceRequests = serviceRequests;
    }

    public String getFather_name() {
        return father_name;
    }

    public void setFather_name(String father_name) {
        this.father_name = father_name;
    }
}
