package Dom.project.Domain_layer.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import Dom.project.Domain_layer.exception.InvalidAddressException;
import Dom.project.Domain_layer.exception.InvalidCompanyException;
import Dom.project.Domain_layer.exception.InvalidUserException;

public class Company {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String name;
    private String inn;
    private String kpp;
    private Address legalAddress;
    private String email;
    private List<User> members;
    private List<User> workers;
    

    //Конструкторы
    public Company() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
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

    public void setCreatedAt(LocalDateTime createdAt) {
        if (createdAt == null) {
            throw new IllegalArgumentException("Created date cannot be null");
        }
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt != null ? updatedAt : LocalDateTime.now();
    }

    private void setUpdatedAt() {
        this.updatedAt = LocalDateTime.now();
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

    public void setMembers(List<User> members) {
        this.members = members != null ? members : new ArrayList<>();
        setUpdatedAt();
    }

    public void setWorkers(List<User> workers) {
        this.workers = workers != null ? workers : new ArrayList<>();
        setUpdatedAt();
    }

    // Геттеры
    public Long getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public String getName() {
        return name;
    }

    public String getInn() {
        return inn;
    }

    public String getKpp() {
        return kpp;
    }

    public Address getLegalAddress() {
        return legalAddress;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return name;
    }

    public String getInnKpp() {
        if (kpp != null && !kpp.isEmpty()) {
            return inn + "/" + kpp;
        }
        return inn;
    }

    public List<User> getMembers() {
        return Collections.unmodifiableList(members);
    }

    public List<User> getWorkers() {
        return Collections.unmodifiableList(workers);
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

    public void addMember(User user) {
        if (user == null) {
            throw InvalidUserException.userCannotBeNull();
        }
        if (members.contains(user)) {
            throw InvalidCompanyException.userAlreadyMember(user.getId());
        }
        members.add(user);
        setUpdatedAt();
    }

    public void removeMember(User user) {
        if (user == null) {
            throw InvalidUserException.userCannotBeNull();
        }
        if (!members.contains(user)) {
            throw InvalidCompanyException.userNotMember(user.getId());
        }
        members.remove(user);
        setUpdatedAt();
    }

    public void clearMembers() {
        members.clear();
        setUpdatedAt();
    }

    public void addWorker(User user) {
        if (user == null) {
            throw InvalidUserException.userCannotBeNull();
        }
        if (workers.contains(user)) {
            throw InvalidCompanyException.userAlreadyWorker(user.getId());
        }
        workers.add(user);
        setUpdatedAt();
    }

    public void removeWorker(User user) {
        if (user == null) {
            throw InvalidUserException.userCannotBeNull();
        }
        if (!workers.contains(user)) {
            throw InvalidCompanyException.userNotWorker(user.getId());
        }
        workers.remove(user);
        setUpdatedAt();
    }

    public void clearWorkers() {
        workers.clear();
        setUpdatedAt();
    }

    //TODO: Почистить
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw InvalidCompanyException.passwordCannotBeEmpty();
        }
        if (password.length() < 6) {
            throw InvalidCompanyException.passwordTooShort(6);
        }
        this.password = password;
        setUpdatedAt();
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

