package Dom.project.Domain_layer.model;

import Dom.project.Domain_layer.exception.InvalidLodgerException;
import Dom.project.Domain_layer.exception.InvalidAddressException;
import java.util.Date;
import java.util.Objects;

public class Lodger {
    private Long id;
    private Address address;
    private Date createdAt;
    private Date updatedAt;

    // Конструкторы
    public Lodger() {
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    public Lodger(Address address) {
        this();
        setAddress(address);
    }

    public Lodger(Long id, Address address) {
        this(address);
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

    public void setAddress(Address address) {
        if (address == null) {
            throw InvalidAddressException.addressCannotBeNull();
        }
        this.address = address;
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

    // Геттеры
    public Long getId() {
        return id;
    }

    public Address getAddress() {
        return address;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public String getFullAddress() {
        if (address == null) {
            return "Адрес не указан";
        }
        return address.getFullAddress();
    }


    public void update(Lodger newLodger) {
        if (newLodger == null) {
            throw InvalidLodgerException.lodgerCannotBeNull();
        }

        if (newLodger.getAddress() != null &&
                !Objects.equals(this.address, newLodger.getAddress())) {
            setAddress(newLodger.getAddress());
        }
    }

    public Lodger copy() {
        Lodger copy = new Lodger();
        copy.setId(this.id);
        if (this.address != null) {
            copy.setAddress(Address.copyOf(this.address));
        }
        copy.setCreatedAt(this.createdAt);
        copy.setUpdatedAt(this.updatedAt);
        return copy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lodger lodger = (Lodger) o;
        return Objects.equals(id, lodger.id) ||
                (Objects.equals(address, lodger.address));
    }

    @Override
    public int hashCode() {
        return id != null ? Objects.hash(id) : Objects.hash(address);
    }

    @Override
    public String toString() {
        return "Lodger{" +
                "id=" + id +
                ", address=" + (address != null ? address.getFullAddress() : "null") +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

}

