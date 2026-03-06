package Dom.project.Infrastructure_layer.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

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

    @Column(name="legal_address")
    @Length(min = 4, max = 255, message = "Некорректная длинна")
    @NotBlank(message = "Адрес компании не может быть пустым")
    String legal_address;

    @Email(message = "Некорректный email")
    @Column(name="email", unique = true)
    @Length(min = 4, max = 255, message = "Некорректная длинна")
    @NotBlank(message = "Email не может быть пустым")
    String email;

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

    public String getLegal_address() {
        return legal_address;
    }

    public void setLegal_address(String legal_address) {
        this.legal_address = legal_address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
