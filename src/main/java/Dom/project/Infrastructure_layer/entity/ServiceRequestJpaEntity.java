package Dom.project.Infrastructure_layer.entity;

import Dom.project.Domain_layer.enums.RequestStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Entity
public class ServiceRequestJpaEntity extends Base_entity{


    @Column(name = "title")
    @Length(max = 255)
    @NotBlank(message = "Это поле не может быть пустым")
    private String title;

    @Length(max = 255)
    @Column(name = "description")
    private String description;

    @Column(name="status")
    private RequestStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_creator")
    private UserJpaEntity assignedWorkerName;
    // TODO: добавить это поле в БД
    // TODO: дата завершения

    @Length(max=1024)
    private String resolutionComment;

    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "id_assignee")
    private UserJpaEntity user;

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

    public UserJpaEntity getAssignedWorkerName() {
        return assignedWorkerName;
    }

    public void setAssignedWorkerName(String assignedWorkerName) {
        assignedWorkerName = assignedWorkerName;
    }

    public String getResolutionComment() {
        return resolutionComment;
    }

    public void setResolutionComment(String resolutionComment) {
        resolutionComment = resolutionComment;
    }

    public UserJpaEntity getUser() {
        return user;
    }

    public void setUser(UserJpaEntity user) {
        this.user = user;
    }

}
