package Dom.project.Web_layer.api.dto;

import java.time.LocalDateTime;

public class UserCountersDto {
    private Long id;
    private String name;
    private String value;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isApproved;
    private UserProfileDto owner;

    public UserCountersDto() {}

    public UserCountersDto(String name, String value, LocalDateTime createdAt,
                           LocalDateTime updatedAt, Boolean isApproved,
                           UserProfileDto owner) {
        this.name = name;
        this.value = value;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isApproved = isApproved;
        this.owner = owner;
    }

    //Геттеры и сеттеры
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }  // исправил имя метода
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }  // исправил имя метода

    public Boolean getIsApproved() { return isApproved; }
    public void setIsApproved(Boolean isApproved) { this.isApproved = isApproved; }

    public UserProfileDto getOwner() { return owner; }
    public void setOwner(UserProfileDto userProfileDto) { this.owner = userProfileDto; }

    public void setId(Long id) { this.id = id;}
    public Long getId() {return id;}
}