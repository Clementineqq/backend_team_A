package Dom.project.Infrastructure_layer.entity;

import Dom.project.Domain_layer.enums.RequestStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Entity
@Table(name = "servicetask")
public class ServiceRequestJpaEntity extends Base_entity{

    @Column(name = "title")
    @Length(max = 255)
    @NotBlank(message = "Это поле не может быть пустым")
    private String title;

    @Length(max = 255)
    @Column(name = "description")
    private String description;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_creator")
    private UserJpaEntity creator;

    @Length(max=1024)
    private String resolutionComment;

    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "id_assignee")
    private UserJpaEntity assignee;

    @Column(name = "completed_at")
    private LocalDateTime completed_at;

    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        description = description;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        status = status;
    }

    public UserJpaEntity getCreator() {
        return creator;
    }

    public String getResolutionComment() {
        return resolutionComment;
    }

    public void setResolutionComment(String resolutionComment) {
        resolutionComment = resolutionComment;
    }

    public UserJpaEntity getAssignee() {
        return assignee;
    }

    public void setAssignee(UserJpaEntity user) {
        this.assignee = user;
    }

    public void setCreator(UserJpaEntity assignedWorkerName) {
        this.creator = assignedWorkerName;
    }

    public LocalDateTime getCompleted_at() {
        return completed_at;
    }

    public void setCompleted_at(LocalDateTime completed_at) {
        this.completed_at = completed_at;
    }
}
