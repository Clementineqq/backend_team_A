package Dom.project.Web_layer.api.dto;

import java.time.LocalDateTime;

public class ServiceRequestDto {
    private String title;
    private String description;
    private String status;
    private String creator;
    private String assigner;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime completedAt;

    public ServiceRequestDto() {}

    public ServiceRequestDto(String title, String description, String status, String creator, String assigner,
                             LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime completedAt) {

        this.title = title;
        this.description = description;
        this.creator = creator;
        this.assigner = assigner;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.completedAt = completedAt;
    }

    // Геттеры и сеттеры
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCreator() { return creator; }
    public void setCreator(String creator) { this.creator = creator; }

    public String getAssigner() { return assigner; }
    public void setAssigner(String assigner) { this.assigner = assigner; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public LocalDateTime getCompletedAt() { return completedAt; }
    public void setCompletedAt(LocalDateTime completedAt) { this.completedAt = completedAt; }
}