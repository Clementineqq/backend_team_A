package Dom.project.Infrastructure_layer.entity;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "company")
public class CompanyJpaEntity extends Base_entity {
    @Column(name = "name")
    @Length(min = 4, max = 255, message = "Некорректная длинна")
    @NotBlank(message = "Имя компании не может быть пустым")
    String name;

    @Column(length = 10, name="inn", unique = true)
    @Length(min = 10, max = 10, message = "Некорректная длинна")
    @NotBlank(message = "ИНН компании не может быть пустым")
    String inn;

    @Column(length = 9, name="kpp")
    @Length(min=9, max=9)
    @NotBlank(message = "КПП компании не может быть пустым")
    String kpp;

    @Column(length = 1024, name="description")
    String description;

    @Email(message = "Некорректный email")
    @Column(name="email", unique = true)
    @Length(min = 4, max = 255, message = "Некорректная длинна")
    @NotBlank(message = "Email не может быть пустым")
    String email;

    @OneToOne
    @JoinColumn(name = "id_address")
    AddressJpaEntity legal_address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getKpp() {
        return kpp;
    }

    public void setKpp(String kpp) {
        this.kpp = kpp;
    }

    public String getDescription() {return description;}

    public void setDescription(String description) { this.description = description; }

    public AddressJpaEntity getLegal_address() {
        return legal_address;
    }

    public void setLegal_address(AddressJpaEntity address) {
        this.legal_address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
}
