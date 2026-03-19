package Dom.project.Infrastructure_layer.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "address")
public class AddressJpaEntity extends Base_entity {

    @Column(name = "region")
    @NotBlank(message = "This field cant be empty, region")
    String region;
    @Column(name = "city")
    @NotBlank(message = "This field cant be empty, city")
    String city;
    @Column(name = "street")
    @NotBlank(message = "This field cant be empty, street")
    String street;
    @Column(name = "house")
    @NotBlank(message = "This field cant be empty, house")
    String house;
    @Column(name = "flat")
    @NotBlank(message = "This field cant be empty, flat")
    String flat;

    @Column(name = "totalarea")
    @NotBlank(message = "This field cant be empty, totalarea")
    String totalArea;

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public String getTotalArea() {
        return totalArea;
    }

    public void setTotalArea(String totalArea) {
        this.totalArea = totalArea;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }


    @Override
    public String toString() {
        return "AddressJpaEntity{" +
                ", region='" + region + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", house='" + house + '\'' +
                ", flat='" + flat + '\'' +
                ", totalArea=" + totalArea +
                '}';
    }
}
