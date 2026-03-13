package Dom.project.Infrastructure_layer.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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

    @Column(unique = true, length = 20, name = "phone_number")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Некорректный формат телефона")
    private String phoneNumber;

    @NotBlank
    @Column(unique = true, nullable = false)
    @Email(message = "Некорректный email")
    private String email;

    @OneToOne()
    @JoinColumn(name="id_address")
    private AddressJpaEntity address;

    @OneToMany()
    @JoinColumn(name="id_counter")
    private List<CounterJpaEntity> counters = new ArrayList<>();

    @ManyToOne()
    @JoinColumn(name="id_company")
    private CompanyJpaEntity company;

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
        return phoneNumber;
    }

    public void setPhone(String phone) {
        this.phoneNumber = phone;
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

    public CompanyJpaEntity getCompany() {
        return company;
    }

    public void setCompany(CompanyJpaEntity company) {
        this.company = company;
    }

    public String getFather_name() {
        return father_name;
    }

    public void setFather_name(String father_name) {
        this.father_name = father_name;
    }
}
