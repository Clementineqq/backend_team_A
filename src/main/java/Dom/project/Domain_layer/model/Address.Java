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
    private String city;
    private String region;
    private String totalArea;


    // Конструкторы
    public Address() {
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    public Address(String street, String house, String city) {
        this();
        setStreet(street);
        setHouse(house);
        setCity(city);
        this.flat = null;
        this.region = null;
        this.totalArea = null;
    }

    public Address(String street, String house, String flat, String city) {
        this(street, house, city);
        setFlat(flat);
    }

    public Address(String street, String house, String flat, String city, String region, String totalArea) {
        this(street, house, flat, city);
        setRegion(region);
        setTotalArea(totalArea);
    }

    public Address(Long id, String street, String house, String flat, String city, String region, String totalArea) {
        this(street, house, flat, city, region, totalArea);
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

    public void setStreet(String street) {
        if (street == null || street.trim().isEmpty()) {
            throw InvalidAddressException.streetCannotBeEmpty();
        }
        String trimmedStreet = street.trim();
        if (trimmedStreet.length() < 2 || trimmedStreet.length() > 100) {
            throw InvalidAddressException.invalidStreetLength();
        }
        this.street = trimmedStreet;
        setUpdatedAt();
    }

    public void setHouse(String house) {
        if (house == null || house.trim().isEmpty()) {
            throw InvalidAddressException.houseCannotBeEmpty();
        }
        String trimmedHouse = house.trim();
        if (trimmedHouse.length() < 1 || trimmedHouse.length() > 20) {
            throw InvalidAddressException.invalidHouseLength();
        }
        this.house = trimmedHouse;
        setUpdatedAt();
    }

    public void setFlat(String flat) {
        if (flat != null && !flat.trim().isEmpty()) {
            String trimmedFlat = flat.trim();
            if (trimmedFlat.length() > 10) {
                throw InvalidAddressException.invalidFlatLength();
            }
            this.flat = trimmedFlat;
        } else {
            this.flat = null;
        }
        setUpdatedAt();
    }

    public void setCity(String city) {
        if (city == null || city.trim().isEmpty()) {
            throw InvalidAddressException.cityCannotBeEmpty();
        }
        String trimmedCity = city.trim();
        if (trimmedCity.length() < 2 || trimmedCity.length() > 100) {
            throw InvalidAddressException.invalidCityLength();
        }
        this.city = trimmedCity;
        setUpdatedAt();
    }

    public void setRegion(String region) {
        if (region != null && !region.trim().isEmpty()) {
            String trimmedRegion = region.trim();
            if (trimmedRegion.length() < 2 || trimmedRegion.length() > 100) {
                throw InvalidAddressException.invalidRegionLength();
            }
            this.region = trimmedRegion;
        } else {
            this.region = null;
        }
        setUpdatedAt();
    }

    public void setTotalArea(String totalArea) {
        if (totalArea != null && !totalArea.trim().isEmpty()) {
            String trimmedArea = totalArea.trim();
            // Проверка что это число с плавающей точкой
            try {
                Double.parseDouble(trimmedArea);
            } catch (NumberFormatException e) {
                throw InvalidAddressException.invalidAreaFormat();
            }
            this.totalArea = trimmedArea;
        } else {
            this.totalArea = null;
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
    public String getCity() { return city; }
    public String getRegion() { return region; }
    public String getTotalArea() { return totalArea; }


    // Бизнес - логика

    public String getFullAddress() {
        StringBuilder fullAddress = new StringBuilder();

        if (region != null && !region.isEmpty()) {
            fullAddress.append(region).append(", ");
        }

        fullAddress.append("г. ").append(city != null ? city : "не указан");
        fullAddress.append(", ул. ").append(street);
        fullAddress.append(", д. ").append(house);

        if (hasFlat()) {
            fullAddress.append(", кв./офис ").append(flat);
        }

        return fullAddress.toString();
    }

    public String getShortAddress() {
        StringBuilder shortAddress = new StringBuilder();
        shortAddress.append("г. ").append(city != null ? city : "не указан");
        shortAddress.append(", ул. ").append(street);
        shortAddress.append(", д. ").append(house);

        if (hasFlat()) {
            shortAddress.append(", кв./офис ").append(flat);
        }

        return shortAddress.toString();
    }

    public boolean hasFlat() {
        return flat != null && !flat.trim().isEmpty();
    }

    public boolean equalsIgnoreId(Address other) {
        if (other == null) return false;

        return Objects.equals(street, other.street) &&
                Objects.equals(house, other.house) &&
                Objects.equals(flat, other.flat) &&
                Objects.equals(city, other.city) &&
                Objects.equals(region, other.region) &&
                Objects.equals(totalArea, other.totalArea);
    }

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

        if (!Objects.equals(this.city, newAddress.getCity())) {
            setCity(newAddress.getCity());
            updated = true;
        }

        if (!Objects.equals(this.region, newAddress.getRegion())) {
            setRegion(newAddress.getRegion());
            updated = true;
        }

        if (!Objects.equals(this.totalArea, newAddress.getTotalArea())) {
            setTotalArea(newAddress.getTotalArea());
            updated = true;
        }

        if (updated) {
            setUpdatedAt();
        }
    }

    public static Address copyOf(Address other) {
        if (other == null) return null;
        return new Address(
                other.getStreet(),
                other.getHouse(),
                other.getFlat(),
                other.getCity(),
                other.getRegion(),
                other.getTotalArea()
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
                        Objects.equals(flat, address.flat) &&
                        Objects.equals(city, address.city) &&
                        Objects.equals(region, address.region));
    }

    @Override
    public int hashCode() {
        return id != null ? Objects.hash(id) : Objects.hash(street, house, flat, city, region);
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", fullAddress='" + getFullAddress() + '\'' +
                ", city='" + city + '\'' +
                ", region='" + region + '\'' +
                ", totalArea='" + totalArea + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

}

