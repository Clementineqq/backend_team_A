package Dom.project.Domain_layer.model;

import Dom.project.Domain_layer.exception.InvalidUserException;
import Dom.project.Domain_layer.exception.InvalidCompanyException;
import Dom.project.Domain_layer.exception.InvalidMemberException;
import Dom.project.Domain_layer.exception.InvalidLodgerException;
import Dom.project.Domain_layer.exception.InvalidCounterException;
import Dom.project.Domain_layer.enums.UserRole;

import java.util.Date;
import java.util.Objects;

public class User {
    private Long id;
    private Date createdAt;
    private Date updatedAt;
    private String phone_number;
    private String email;
    private String password;
    private String name;
    private String lastName;
    private String fatherName;
    private Member member;
    private Lodger lodger;
    private Counter counter;
    private Company company;

    public User() {
        this.createdAt = new Date();
        this.updatedAt = new Date();
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

    public void setPhone_number(String phone_number) {
        if (phone_number == null || phone_number.trim().isEmpty()) {
            throw InvalidUserException.phoneCannotBeEmpty();
        }
        String trimmed = phone_number.trim().replaceAll("\\s", "");

        this.phone_number = trimmed;
        setUpdatedAt();
    }

    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw InvalidUserException.emailCannotBeEmpty();
        }
        String trimmed = email.trim().toLowerCase();

        this.email = trimmed;
        setUpdatedAt();
    }

    public void setPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw InvalidUserException.passwordCannotBeEmpty();
        }
        // В реальном проекте здесь должно быть хэширование пароля
        this.password = password; // TODO: Добавить хэширование
        setUpdatedAt();
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw InvalidUserException.nameCannotBeEmpty();
        }
        String trimmed = name.trim();

        this.name = trimmed;
        setUpdatedAt();
    }

    public void setLastName(String lastName) {
        if (lastName == null || lastName.trim().isEmpty()) {
            throw InvalidUserException.lastNameCannotBeEmpty();
        }
        String trimmed = lastName.trim();

        this.lastName = trimmed;
        setUpdatedAt();
    }

    public void setFatherName(String fatherName) {
        if (fatherName != null && !fatherName.trim().isEmpty()) {
            String trimmed = fatherName.trim();

            this.fatherName = trimmed;
        } else {
            this.fatherName = null;
        }
        setUpdatedAt();
    }

    public void setMember(Member member) {
        this.member = member;
        setUpdatedAt();
    }

    public void setLodger(Lodger lodger) {
        this.lodger = lodger;
        setUpdatedAt();
    }

    public void setCounter(Counter counter) {
        this.counter = counter;
        setUpdatedAt();
    }

    public void setCompany(Company company) {
        if (company == null) {
            throw InvalidCompanyException.companyCannotBeNull();
        }
        this.company = company;
        setUpdatedAt();
    }

    // Геттеры
    public Long getId() { return id; }
    public Date getCreatedAt() { return createdAt; }
    public Date getUpdatedAt() { return updatedAt; }
    public String getPhone_number() { return phone_number; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getName() { return name; }
    public String getLastName() { return lastName; }
    public String getFatherName() { return fatherName; }
    public Member getMember() { return member; }
    public Lodger getLodger() { return lodger; }
    public Counter getCounter() { return counter; }
    public Company getCompany() { return company; }


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


    public boolean isMember() {
        return member != null;
    }


    public boolean isLodger() {
        return lodger != null;
    }


    public boolean hasCounter() {
        return counter != null;
    }


    public boolean hasCompany() {
        return company != null;
    }


    public UserRole getUserRole() {
        if (member != null && member.getRole() != null) {
            return member.getRole().getRoleName();
        }
        return null;
    }


    public boolean checkPassword(String rawPassword) {
        if (rawPassword == null) return false;
        // TODO: Implement proper password hashing comparison
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


    public void assignCounter(Counter counter) {
        if (counter == null) {
            throw InvalidCounterException.counterNotFound(null);
        }
        if (this.counter != null) {
            throw InvalidUserException.alreadyHasCounter();
        }
        this.counter = counter;
        setUpdatedAt();
    }


    public Lodger createLodger(Address address) {
        if (this.lodger != null) {
            throw InvalidUserException.alreadyHasLodger();
        }
        this.lodger = new Lodger(address);
        setUpdatedAt();
        return this.lodger;
    }


    public Member createMember(Role role) {
        if (this.member != null) {
            throw InvalidUserException.alreadyHasMember();
        }
        this.member = new Member(role);
        setUpdatedAt();
        return this.member;
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
                "id=" + id +
                ", fullName='" + getFullName() + '\'' +
                ", phone='" + phone_number + '\'' +
                ", email='" + email + '\'' +
                ", hasMember=" + (member != null) +
                ", hasLodger=" + (lodger != null) +
                '}';
    }

}

