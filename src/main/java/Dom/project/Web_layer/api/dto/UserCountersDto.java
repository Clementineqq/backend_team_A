package Dom.project.Web_layer.api.dto;

import java.time.LocalDateTime;

public class UserCountersDto {
    private String name;
    private String value;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAd;
    private Boolean isApproved;
    private UserProfileDto userProfileDto;


    public UserCountersDto() {}



    // Геттеры и сеттеры
    private String getName() { return name;}
    private void setName(String name) {this.name = name;}

    private String getValue() { return value;}
    private void setValue(String value) {this.value = value;}

    private LocalDateTime getCreatedAt() { return createdAt;}
    private void setCreatedAt(LocalDateTime createdAt) {this.createdAt = createdAt;}

    private LocalDateTime getUpdatedAd() { return updatedAd;}
    private void setUpdatedAd(LocalDateTime updatedAd) {this.updatedAd = updatedAd;}

    private Boolean getIsApproved() { return isApproved;}
    private void setIsApproved(Boolean isApproved) {this.isApproved = isApproved;}

    private UserProfileDto getUserProfileDto() { return userProfileDto;}
    private void setUserProfileDto(UserProfileDto userProfileDto) {this.userProfileDto = userProfileDto;}
}