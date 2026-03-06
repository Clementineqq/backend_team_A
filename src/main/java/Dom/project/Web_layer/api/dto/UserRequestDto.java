package Dom.project.Web_layer.api.dto;

import java.time.LocalDateTime;

public class UserRequestDto {
    private Long id;
    private String title;
    private String description;
    private String status; // NEW, IN_PROGRESS, COMPLETED, CANCELLED
    private String type; // REPAIR, CONSULTATION, MAINTENANCE
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UserRequestDto() {}

    public UserRequestDto(Long id, String title, String description, String status,
                          String type, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.type = type;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Геттеры и сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}