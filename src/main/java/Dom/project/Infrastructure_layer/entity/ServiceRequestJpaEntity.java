package Dom.project.Infrastructure_layer.entity;

import Dom.project.Domain_layer.enums.RequestStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ServiceRequestJpaEntity extends Base_entity{


    private String Title;
    private String Description;
    private RequestStatus Status;

    private String AssignedWorkerName;
    private String ResolutionComment;

    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "user_id")      
    private UserJpaEntity user;
    

    
    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public RequestStatus getStatus() {
        return Status;
    }

    public void setStatus(RequestStatus status) {
        Status = status;
    }

    public String getAssignedWorkerName() {
        return AssignedWorkerName;
    }

    public void setAssignedWorkerName(String assignedWorkerName) {
        AssignedWorkerName = assignedWorkerName;
    }

    public String getResolutionComment() {
        return ResolutionComment;
    }

    public void setResolutionComment(String resolutionComment) {
        ResolutionComment = resolutionComment;
    }

    public UserJpaEntity getUser() {
        return user;
    }

    public void setUser(UserJpaEntity user) {
        this.user = user;
    }

}
