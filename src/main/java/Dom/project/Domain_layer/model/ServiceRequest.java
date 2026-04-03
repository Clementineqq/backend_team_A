package Dom.project.Domain_layer.model;

import Dom.project.Domain_layer.enums.RequestStatus;
import Dom.project.Domain_layer.exception.InvalidTaskException;
import Dom.project.Domain_layer.exception.InvalidUserException;

import java.time.LocalDateTime;
import java.util.Objects;

public class ServiceRequest {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String title;
    private String description;
    private RequestStatus requestStatus;
    private User creator;
    private User assigner;
    private LocalDateTime completedAt;
    private String resolutionComment;

    public ServiceRequest() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.requestStatus = RequestStatus.Created;
        this.completedAt = null;
    }

    public ServiceRequest(String title, User creator) {
        this();
        setTitle(title);
        setCreator(creator);
    }

    public ServiceRequest(String title, String description, User creator) {
        this(title, creator);
        setDescription(description);
    }

    public ServiceRequest(Long id, String title, User creator) {
        this(title, creator);
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

    public void setCreatedAt(LocalDateTime createdAt) {
        if (createdAt == null) {
            throw new IllegalArgumentException("Created date cannot be null");
        }
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt != null ? updatedAt : LocalDateTime.now();
    }

    private void setUpdatedAt() {
        this.updatedAt = LocalDateTime.now();
    }

    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw InvalidTaskException.titleCannotBeEmpty();
        }
        if (title.length() < 3 || title.length() > 255) {
            throw InvalidTaskException.invalidTitleLength();
        }
        this.title = title.trim();
        setUpdatedAt();
    }

    public void setDescription(String description) {
        if (description != null && !description.trim().isEmpty()) {
            this.description = description.trim();
        } else {
            this.description = null;
        }
        setUpdatedAt();
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        if (requestStatus == null) {
            throw InvalidTaskException.statusCannotBeNull();
        }
        this.requestStatus = requestStatus;
        setUpdatedAt();
    }

    public void setCreator(User creator) {
        if (creator == null) {
            throw InvalidUserException.userCannotBeNull();
        }
        this.creator = creator;
        setUpdatedAt();
    }

    public void setAssigner(User assigner) {
        this.assigner = assigner;
        setUpdatedAt();
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
        setUpdatedAt();
    }

    public void setResolutionComment(String resolutionComment) {
        this.resolutionComment = resolutionComment;
    }

    // Геттеры
    public Long getId() { return id; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public RequestStatus getRequestStatus() { return requestStatus; }
    public User getCreator() { return creator; }
    public User getAssigner() { return assigner; }
    public LocalDateTime getCompletedAt() { return completedAt; }
    public String getResolutionComment() { return resolutionComment; }
    public String getStringRequestStatus() { return  requestStatus.toString(); }



    public ServiceRequest copy() {
        ServiceRequest copy = new ServiceRequest();
        copy.setId(this.id);
        copy.setTitle(this.title);
        copy.setDescription(this.description);
        copy.setRequestStatus(this.requestStatus);
        copy.setCreator(this.creator);
        copy.setAssigner(this.assigner);
        copy.setCompletedAt(this.completedAt);
        copy.setCreatedAt(this.createdAt);
        copy.setUpdatedAt(this.updatedAt);
        return copy;
    }


    public String getSummary() {
        return String.format("Задача #%d: %s (%s)",
                id, title, getDescription());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceRequest that = (ServiceRequest) o;
        return Objects.equals(id, that.id) ||
                (Objects.equals(title, that.title) &&
                        Objects.equals(creator, that.creator) &&
                        Objects.equals(createdAt, that.createdAt));
    }

    @Override
    public int hashCode() {
        return id != null ? Objects.hash(id) : Objects.hash(title, creator, createdAt);
    }

    @Override
    public String toString() {
        return "serviceTask{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", status=" + requestStatus +
                ", creator=" + (creator != null ? creator.getFullName() : "null") +
                ", assigner=" + (assigner != null ? assigner.getFullName() : "null") +
                ", completedAt=" + completedAt +
                '}';
    }
}

