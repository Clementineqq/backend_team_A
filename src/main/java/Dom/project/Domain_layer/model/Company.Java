package Dom.project.Domain_layer.model;

import Dom.project.Domain_layer.exception.InvalidAddressException;
import Dom.project.Domain_layer.exception.InvalidCompanyException;
import java.util.Date;
import java.util.Objects;

public class Company {
    private Long id;
    private Date createdAt;
    private Date updatedAt;
    private String name;
    private String inn;
    private String kpp;
    private Address legalAddress;
    private String email;


    //Конструкторы
    public Company() {
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    public Company(String name, String inn) {
        this();
        setName(name);
        setInn(inn);
    }

    public Company(String name, String inn, Address legalAddress) {
        this(name, inn);
        setLegalAddress(legalAddress);
    }

    public Company(String name, String inn, String kpp, Address legalAddress, String email) {
        this(name, inn, legalAddress);
        setKpp(kpp);
        setEmail(email);
    }

    public Company(Long id, String name, String inn, Address legalAddress) {
        this(name, inn, legalAddress);
        this.id = id;
    }

    // Сеттеры с валидацией
    public void setId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID must be positive");
        }
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

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw InvalidCompanyException.nameCannotBeEmpty();
        }
        String trimmed = name.trim();
        if (trimmed.length() < 2 || trimmed.length() > 255) {
            throw InvalidCompanyException.invalidNameLength();
        }
        this.name = trimmed;
        setUpdatedAt();
    }

    public void setInn(String inn) {
        if (inn == null || inn.trim().isEmpty()) {
            throw InvalidCompanyException.innCannotBeEmpty();
        }
        String trimmed = inn.trim().replaceAll("\\s", "");
        if (!trimmed.matches("^[0-9]{10}$|^[0-9]{12}$")) {
            throw InvalidCompanyException.invalidInnFormat(inn);
        }
        this.inn = trimmed;
        setUpdatedAt();
    }

    public void setKpp(String kpp) {
        if (kpp != null && !kpp.trim().isEmpty()) {
            String trimmed = kpp.trim().replaceAll("\\s", "");
            if (!trimmed.matches("^[0-9]{9}$")) {
                throw InvalidCompanyException.invalidKppFormat(kpp);
            }
            this.kpp = trimmed;
        } else {
            this.kpp = null;
        }
        setUpdatedAt();
    }

    public void setLegalAddress(Address legalAddress) {
        if (legalAddress == null) {
            throw InvalidAddressException.addressCannotBeNull();
        }
        this.legalAddress = legalAddress;
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
    public String getName() { return name; }
    public String getInn() { return inn; }
    public String getKpp() { return kpp; }
    public Address getLegalAddress() { return legalAddress; }
    public String getEmail() { return email; }

    public String getFullName() {
        return name;
    }

    public String getInnKpp() {
        if (kpp != null && !kpp.isEmpty()) {
            return inn + "/" + kpp;
        }
        return inn;
    }

    public void update(Company newData) {
        if (newData == null) {
            throw InvalidCompanyException.companyCannotBeNull();
        }

        boolean updated = false;

        if (newData.getName() != null && !newData.getName().equals(this.name)) {
            setName(newData.getName());
            updated = true;
        }

        if (newData.getLegalAddress() != null && !newData.getLegalAddress().equals(this.legalAddress)) {
            setLegalAddress(newData.getLegalAddress());
            updated = true;
        }

        if (newData.getEmail() != null && !newData.getEmail().equals(this.email)) {
            setEmail(newData.getEmail());
            updated = true;
        }

        // ИНН и КПП обычно не меняются, но если нужно:
        if (newData.getInn() != null && !newData.getInn().equals(this.inn)) {
            setInn(newData.getInn());
            updated = true;
        }

        if (newData.getKpp() != null && !newData.getKpp().equals(this.kpp)) {
            setKpp(newData.getKpp());
            updated = true;
        }

        if (updated) {
            setUpdatedAt();
        }
    }

    public Company copy() {
        Company copy = new Company();
        copy.setId(this.id);
        copy.setName(this.name);
        copy.setInn(this.inn);
        copy.setKpp(this.kpp);
        copy.setLegalAddress(this.legalAddress != null ? Address.copyOf(this.legalAddress) : null);
        copy.setEmail(this.email);
        copy.setCreatedAt(this.createdAt);
        copy.setUpdatedAt(this.updatedAt);
        return copy;
    }

    public String getSummary() {
        return String.format("%s (ИНН: %s)", name, inn);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(id, company.id) ||
                (Objects.equals(inn, company.inn));
    }

    @Override
    public int hashCode() {
        return id != null ? Objects.hash(id) : Objects.hash(inn);
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", inn='" + inn + '\'' +
                ", kpp='" + kpp + '\'' +
                ", city='" + (legalAddress != null ? legalAddress.getCity() : "null") + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

}

