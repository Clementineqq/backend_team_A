package Dom.project.Infrastructure_layer.entity;

import jakarta.persistence.*;

@Entity
@Table(name="lodger")
public class ApartmentJpaEntity extends Base_entity {

    private String ApartmentNumber;
    private String BuildingAddress;
    private double TotalArea;

    @OneToOne
    @JoinColumn(name = "address_id")
    private AddressJpaEntity address;

     public String getApartmentNumber() {
         return ApartmentNumber;
     }

     public void setApartmentNumber(String apartmentNumber) {
         ApartmentNumber = apartmentNumber;
     }

     public String getBuildingAddress() {
         return BuildingAddress;
     }

     public void setBuildingAddress(String buildingAddress) {
         BuildingAddress = buildingAddress;
     }

     public double getTotalArea() {
         return TotalArea;
     }

     public void setTotalArea(double totalArea) {
         TotalArea = totalArea;
     }

    public AddressJpaEntity getAddress() {
        return address;
    }

    public void setAddress(AddressJpaEntity address) {
        this.address = address;
    }
}
