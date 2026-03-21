package Dom.project.Domain_layer.model;

import Dom.project.Domain_layer.exception.*;
import Dom.project.Domain_layer.enums.UserRole;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

public class User {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String phone_number;
    private String email;
    private String password;
    private String name;
    private String lastName;
    private String fatherName;
    private UserRole role;
    private Address address;
    private Company company;

    public User() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public User(String phone_number, String email, String password, String name, String lastName) {
        this();
        setPhone_number(phone_number);
        setEmail(email);
        setPassword(password);
        setName(name);
        setLastName(lastName);

    }

    public User(Long id, String phone_number, String email, String password, String name, String lastName) {
        this(phone_number, email, password, name, lastName);
        this.id = id;
    }

    public User(Long id, String phone_number, String email, String password, String name, String lastName, UserRole role) {
        this(id, phone_number, email, password, name, lastName);
        this.role = role;
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

    public void setPhone_number(String phone_number) {
        if (phone_number == null || phone_number.trim().isEmpty()) {
            throw InvalidUserException.phoneCannotBeEmpty();
        }
        String trimmed = phone_number.trim().replaceAll("\\s", "");
        if (!trimmed.matches("^\\+?[0-9]{10,15}$")) {
            throw InvalidUserException.invalidPhoneFormat(phone_number);
        }
        this.phone_number = trimmed;
        setUpdatedAt();
    }

    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw InvalidUserException.emailCannotBeEmpty();
        }
        String trimmed = email.trim().toLowerCase();
        if (!trimmed.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw InvalidUserException.invalidEmailFormat(email);
        }
        this.email = trimmed;
        setUpdatedAt();
    }

    public void setPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw InvalidUserException.passwordCannotBeEmpty();
        }
        if (password.length() < 6) {
            throw InvalidUserException.passwordTooShort(6);
        }
        this.password = password;
        setUpdatedAt();
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw InvalidUserException.nameCannotBeEmpty();
        }
        String trimmed = name.trim();
        if (trimmed.length() < 2 || trimmed.length() > 50) {
            throw InvalidUserException.invalidNameLength();
        }
        this.name = trimmed;
        setUpdatedAt();
    }

    public void setLastName(String lastName) {
        if (lastName == null || lastName.trim().isEmpty()) {
            throw InvalidUserException.lastNameCannotBeEmpty();
        }
        String trimmed = lastName.trim();
        if (trimmed.length() < 2 || trimmed.length() > 50) {
            throw InvalidUserException.invalidNameLength();
        }
        this.lastName = trimmed;
        setUpdatedAt();
    }

    public void setFatherName(String fatherName) {
        if (fatherName != null && !fatherName.trim().isEmpty()) {
            String trimmed = fatherName.trim();
            if (trimmed.length() > 50) {
                throw InvalidUserException.invalidNameLength();
            }
            this.fatherName = trimmed;
        } else {
            this.fatherName = null;
        }
        setUpdatedAt();
    }

    public void setAddress(Address address) {
        if (address == null) {
            throw InvalidAddressException.addressCannotBeNull();
        }
        this.address = address;
        setUpdatedAt();
    }

    public void setCompany(Company company) {
        if (company == null) {
            throw InvalidCompanyException.companyCannotBeNull();
        }
        this.company = company;
        setUpdatedAt();
    }

    public void setRole(UserRole role){
        if (role == null){
            throw InvalidRoleException.roleCantBeNull();
        }

        this.role = role;
        setUpdatedAt();
    }

    // Геттеры
    public Long getId() { return id; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public String getPhone_number() { return phone_number; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getName() { return name; }
    public String getLastName() { return lastName; }
    public String getFatherName() { return fatherName; }
    public Address getAddress() { return address; }
    public Company getCompany() { return company; }
    public UserRole getRole() { return role; }


    public String getFullName() {
        StringBuilder fullName = new StringBuilder();
        fullName.append(lastName).append(" ").append(name);
        if (fatherName != null && !fatherName.isEmpty()) {
            fullName.append(" ").append(fatherName);
        }
        return fullName.toString();
    }

    public String getInitials() {
        StringBuilder initials = new StringBuilder();
        initials.append(lastName).append(" ");
        if (name != null && !name.isEmpty()) {
            initials.append(name.charAt(0)).append(".");
        }
        if (fatherName != null && !fatherName.isEmpty()) {
            initials.append(fatherName.charAt(0)).append(".");
        }
        return initials.toString();
    }

    public boolean hasAddress() {
        return address != null;
    }

    public boolean hasCompany() {
        return company != null;
    }

    public String getFullAddress() {
        return address != null ? address.getFullAddress() : "Адрес не указан";
    }

    public boolean checkPassword(String rawPassword) {
        if (rawPassword == null) return false;
        return this.password.equals(rawPassword);
    }

    public void changePassword(String oldPassword, String newPassword) {
        if (!checkPassword(oldPassword)) {
            throw InvalidUserException.incorrectPassword();
        }
        setPassword(newPassword);
    }

    public void updateProfile(String name, String lastName, String fatherName) {
        boolean updated = false;

        if (name != null && !name.equals(this.name)) {
            setName(name);
            updated = true;
        }

        if (lastName != null && !lastName.equals(this.lastName)) {
            setLastName(lastName);
            updated = true;
        }

        if (fatherName != null && !fatherName.equals(this.fatherName)) {
            setFatherName(fatherName);
            updated = true;
        }

        if (updated) {
            setUpdatedAt();
        }
    }

    public void updateContact(String phone_number, String email) {
        boolean updated = false;

        if (phone_number != null && !phone_number.equals(this.phone_number)) {
            setPhone_number(phone_number);
            updated = true;
        }

        if (email != null && !email.equals(this.email)) {
            setEmail(email);
            updated = true;
        }

        if (updated) {
            setUpdatedAt();
        }
    }

    public void assignToCompany(Company company) {
        if (company == null) {
            throw InvalidCompanyException.companyCannotBeNull();
        }
        if (this.company != null) {
            throw InvalidUserException.alreadyHasCompany();
        }
        this.company = company;
        setUpdatedAt();
    }

    public void removeFromCompany() {
        if (this.company == null) {
            throw InvalidUserException.noCompanyToRemove();
        }
        this.company = null;
        setUpdatedAt();
    }

    public void updateAddress(Address newAddress) {
        if (newAddress == null) {
            throw InvalidAddressException.addressCannotBeNull();
        }
        if (this.address != null) {
            this.address.update(newAddress);
        } else {
            setAddress(newAddress);
        }
        setUpdatedAt();
    }

    public String getContactInfo() {
        return String.format("Тел: %s, Email: %s",
                phone_number != null ? phone_number : "не указан",
                email != null ? email : "не указан");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) ||
                (Objects.equals(phone_number, user.phone_number)) ||
                (Objects.equals(email, user.email));
    }

    @Override
    public int hashCode() {
        return id != null ? Objects.hash(id) : Objects.hash(phone_number, email);
    }

    @Override
    public String toString() {
        return "User{" +
                "company=" + company +
                ", address=" + address +
                ", role=" + role +
                ", fatherName='" + fatherName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", updatedAt=" + updatedAt +
                ", createdAt=" + createdAt +
                ", id=" + id +
                '}';
    }

}

