package Dom.project.Domain_layer.model;

import Dom.project.Domain_layer.exception.InvalidCompanyException;
import java.util.Date;
import java.util.Objects;

public class Company {
    private Long id;
    private Date createdAt;
    private Date updatedAt;
    private String Name;
    private String INN;
    private String KPP;
    private String legal_address;
    private String email;

    public Company() {
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    public Company(String Name, String INN) {
        this();
        setName(Name);
        setINN(INN);
    }

    public Company(String Name, String INN, String KPP) {
        this(Name, INN);
        setKPP(KPP);
    }

    public Company(Long id, String Name, String INN) {
        this(Name, INN);
        this.id = id;
    }

    // Сеттеры с валидацией
    public void setId(Long id) {
        this.id = id;
        setUpdatedAt();
    }

    public void setCreatedAt(Date createdAt) {
        if (createdAt == null) {
            throw new IllegalArgumentException("Created date cannot be null");
        }
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt != null ? updatedAt : new Date();
    }

    private void setUpdatedAt() {
        this.updatedAt = new Date();
    }

    public void setName(String Name) {
        if (Name == null || Name.trim().isEmpty()) {
            throw InvalidCompanyException.nameCannotBeEmpty();
        }
        this.Name = Name.trim();
        setUpdatedAt();
    }

    public void setINN(String INN) {
        if (INN == null || INN.trim().isEmpty()) {
            throw InvalidCompanyException.innCannotBeEmpty();
        }
        String trimmed = INN.trim().replaceAll("\\s", "");
        if (!trimmed.matches("^[0-9]{10}$|^[0-9]{12}$")) {
            throw InvalidCompanyException.invalidInnFormat(INN);
        }
        this.INN = trimmed;
        setUpdatedAt();
    }

    public void setKPP(String KPP) {
        if (KPP != null && !KPP.trim().isEmpty()) {
            String trimmed = KPP.trim().replaceAll("\\s", "");
            if (!trimmed.matches("^[0-9]{9}$")) {
                throw InvalidCompanyException.invalidKppFormat(KPP);
            }
            this.KPP = trimmed;
        } else {
            this.KPP = null;
        }
        setUpdatedAt();
    }

    public void setLegal_address(String legal_address) {
        if (legal_address == null || legal_address.trim().isEmpty()) {
            throw InvalidCompanyException.addressCannotBeEmpty();
        }
        this.legal_address = legal_address.trim();
        setUpdatedAt();
    }

    public void setEmail(String email) {
        if (email != null && !email.trim().isEmpty()) {
            String trimmed = email.trim().toLowerCase();
            if (!trimmed.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                throw InvalidCompanyException.invalidEmailFormat(email);
            }
            this.email = trimmed;
        } else {
            this.email = null;
        }
        setUpdatedAt();
    }

    // Геттеры
    public Long getId() { return id; }
    public Date getCreatedAt() { return createdAt; }
    public Date getUpdatedAt() { return updatedAt; }
    public String getName() { return Name; }
    public String getINN() { return INN; }
    public String getKPP() { return KPP; }
    public String getLegal_address() { return legal_address; }
    public String getEmail() { return email; }

    public String getFullName() {
        return Name;
    }


    public void update(Company newData) {
        if (newData == null) {
            throw InvalidCompanyException.companyCannotBeNull();
        }

        boolean updated = false;

        if (newData.getName() != null && !newData.getName().equals(this.Name)) {
            setName(newData.getName());
            updated = true;
        }

        if (newData.getLegal_address() != null && !newData.getLegal_address().equals(this.legal_address)) {
            setLegal_address(newData.getLegal_address());
            updated = true;
        }

        if (newData.getEmail() != null && !newData.getEmail().equals(this.email)) {
            setEmail(newData.getEmail());
            updated = true;
        }

        // ИНН и КПП обычно не меняются, но если нужно:
        if (newData.getINN() != null && !newData.getINN().equals(this.INN)) {
            setINN(newData.getINN());
            updated = true;
        }

        if (newData.getKPP() != null && !newData.getKPP().equals(this.KPP)) {
            setKPP(newData.getKPP());
            updated = true;
        }

        if (updated) {
            setUpdatedAt();
        }
    }

    public Company copy() {
        Company copy = new Company();
        copy.setId(this.id);
        copy.setName(this.Name);
        copy.setINN(this.INN);
        copy.setKPP(this.KPP);
        copy.setLegal_address(this.legal_address);
        copy.setEmail(this.email);
        copy.setCreatedAt(this.createdAt);
        copy.setUpdatedAt(this.updatedAt);
        return copy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(id, company.id) ||
                (Objects.equals(INN, company.INN));
    }

    @Override
    public int hashCode() {
        return id != null ? Objects.hash(id) : Objects.hash(INN);
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", Name='" + Name + '\'' +
                ", INN='" + INN + '\'' +
                ", KPP='" + KPP + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

}

