package Dom.project.Domain_layer.model;

import Dom.project.Domain_layer.enums.RequestStatus;
import Dom.project.Domain_layer.exception.InvalidTaskException;
import Dom.project.Domain_layer.exception.InvalidUserException;

import java.util.Date;
import java.util.Objects;

public class serviceTask {
    private Long id;
    private Date createdAt;
    private Date updatedAt;
    private String title;
    private String description;
    private RequestStatus requestStatus;
    private User creator;
    private User assigner;
    private Date completedAt;

    public serviceTask() {
        this.createdAt = new Date();
        this.updatedAt = new Date();
        this.requestStatus = RequestStatus.Created;
        this.completedAt = null;
    }

    public serviceTask(String title, User creator) {
        this();
        setTitle(title);
        setCreator(creator);
    }

    public serviceTask(String title, String description, User creator) {
        this(title, creator);
        setDescription(description);
    }

    public serviceTask(Long id, String title, User creator) {
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

    public void setCompletedAt(Date completedAt) {
        this.completedAt = completedAt;
        setUpdatedAt();
    }

    // Геттеры
    public Long getId() { return id; }
    public Date getCreatedAt() { return createdAt; }
    public Date getUpdatedAt() { return updatedAt; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public RequestStatus getRequestStatus() { return requestStatus; }
    public User getCreator() { return creator; }
    public User getAssigner() { return assigner; }
    public Date getCompletedAt() { return completedAt; }





    public serviceTask copy() {
        serviceTask copy = new serviceTask();
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
        serviceTask that = (serviceTask) o;
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

