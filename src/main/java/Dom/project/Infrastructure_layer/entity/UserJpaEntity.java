package Dom.project.Infrastructure_layer.entity;

import java.util.ArrayList;
import java.util.List;

import Dom.project.Domain_layer.enums.UserRole;
import jakarta.persistence.*;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;


@Entity
@Table(name = "users")
public class UserJpaEntity extends Base_entity {
    
    @Column(nullable = false, length = 50)
    @NotBlank(message = "Name cant be empty")
    private String name;

    @Column(nullable = false, length = 50, name="last_name")
    @NotBlank(message = "Last name cant be empty")
    private String surname;

    @Column(name = "fathers_name")
    private String father_name;

    @Column(name="phone_number", unique = true, length = 20)
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Incorrect phone number")
    private String phone;

    @NotBlank
    @Column(unique = true, nullable = false)
    @Email(message = "Incorrect email")
    private String email;

    @OneToOne()
    @JoinColumn(name="id_address")
    private AddressJpaEntity address;

    @ManyToOne()
    @JoinColumn(name="id_company")
    private CompanyJpaEntity company;

    @Column(name = "password")
    @NotBlank(message = "Password cant be empty")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRole role;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
