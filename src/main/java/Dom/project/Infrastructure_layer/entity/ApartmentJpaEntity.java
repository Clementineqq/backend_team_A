package Dom.project.Infrastructure_layer.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class ApartmentJpaEntity extends Base_entity {

    private String ApartmentNumber;
    private String BuildingAddress;
    private double TotalArea;

     @OneToOne
     @JoinColumn(name = "user_id") // Foreign Key
    private UserJpaEntity user;

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

     public UserJpaEntity getUser() {
         return user;
     }

     public void setUser(UserJpaEntity user) {
         this.user = user;
     }
}
