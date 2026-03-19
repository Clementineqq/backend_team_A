package Dom.project.Web_layer.auth.dto;

public class AddressDto {
    private String region;
    private String city;
    private String street;
    private String house;
    private String flat;
    private Float totalArea;

    public AddressDto(String region, String city, String street, String house, String flat, Float totalArea) {
        this.region = region;
        this.city = city;
        this.street = street;
        this.house = house;
        this.flat = flat;
        this.totalArea = totalArea;
    }

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
    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
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
    public Float getTotalArea() {
        return totalArea;
    }
    public void setTotalArea(Float totalArea) {
        this.totalArea = totalArea;
    }

    @Override
    public String toString() {
        return "AddressDto{" +
                "region='" + region + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", house='" + house + '\'' +
                ", flat='" + flat + '\'' +
                ", totalArea=" + totalArea +
                '}';
    }
}
