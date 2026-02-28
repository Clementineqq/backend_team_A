package Dom.project.Domain_layer.model;

import Dom.project.Domain_layer.exception.InvalidAddressException;
import java.util.Date;
import java.util.Objects;

public class Address {
    private Long id;
    private Date createdAt;
    private Date updatedAt;
    private String street;
    private String house;
    private String flat;


    // Конструкторы
    public Address() {
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    public Address(String street, String house) {
        this();
        setStreet(street);
        setHouse(house);
        this.flat = null;
    }

    public Address(String street, String house, String flat) {
        this(street, house);
        setFlat(flat);
    }

    public Address(Long id, String street, String house, String flat) {
        this(street, house, flat);
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

    public void setStreet(String street) {
        if (street == null || street.trim().isEmpty()) {
            throw InvalidAddressException.streetCannotBeEmpty();
        }

        String trimmedStreet = street.trim();

        this.street = trimmedStreet;
        setUpdatedAt();
    }

    public void setHouse(String house) {
        if (house == null || house.trim().isEmpty()) {
            throw InvalidAddressException.houseCannotBeEmpty();
        }

        String trimmedHouse = house.trim();


        this.house = trimmedHouse;
        setUpdatedAt();
    }

    public void setFlat(String flat) {
        if (flat != null && !flat.trim().isEmpty()) {
            String trimmedFlat = flat.trim();
            this.flat = trimmedFlat;
        } else {
            this.flat = null;
        }
        setUpdatedAt();
    }

    // Геттеры
    public Long getId() { return id; }
    public Date getCreatedAt() { return createdAt; }
    public Date getUpdatedAt() { return updatedAt; }
    public String getStreet() { return street; }
    public String getHouse() { return house; }
    public String getFlat() { return flat; }



    // Бизнес - логика

    //Полный адрес одной строкой
    public String getFullAddress() {
        StringBuilder fullAddress = new StringBuilder();
        fullAddress.append("ул. ").append(street);
        fullAddress.append(", д. ").append(house);

        if (hasFlat()) {
            fullAddress.append(", кв./офис ").append(flat);
        }

        return fullAddress.toString();
    }

    //Есть ли квартира
    public boolean hasFlat() {
        return flat != null && !flat.trim().isEmpty();
    }

    public boolean equalsIgnoreId(Address other) {
        if (other == null) return false;

        return Objects.equals(street, other.street) &&
                Objects.equals(house, other.house) &&
                Objects.equals(flat, other.flat);
    }

    // Обновление адреса
    public void update(Address newAddress) {
        if (newAddress == null) {
            throw InvalidAddressException.addressCannotBeNull();
        }

        boolean updated = false;

        if (!Objects.equals(this.street, newAddress.getStreet())) {
            setStreet(newAddress.getStreet());
            updated = true;
        }

        if (!Objects.equals(this.house, newAddress.getHouse())) {
            setHouse(newAddress.getHouse());
            updated = true;
        }

        if (!Objects.equals(this.flat, newAddress.getFlat())) {
            setFlat(newAddress.getFlat());
            updated = true;
        }

        if (updated) {
            setUpdatedAt();
        }
    }

    // Копия адреса
    public static Address copyOf(Address other) {
        if (other == null) return null;
        return new Address(
                other.getStreet(),
                other.getHouse(),
                other.getFlat()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(id, address.id) ||
                (Objects.equals(street, address.street) &&
                        Objects.equals(house, address.house) &&
                        Objects.equals(flat, address.flat));
    }

    @Override
    public int hashCode() {
        return id != null ? Objects.hash(id) : Objects.hash(street, house, flat);
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", street='" + street + '\'' +
                ", house='" + house + '\'' +
                ", flat='" + flat + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

}

