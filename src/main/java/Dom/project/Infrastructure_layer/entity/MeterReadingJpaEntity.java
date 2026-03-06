package Dom.project.Infrastructure_layer.entity;

import Dom.project.Domain_layer.enums.MeterType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class MeterReadingJpaEntity extends Base_entity{

    // TODO: над этим полем подумать, мб расширить таблицу тоже на созвоне
    @NotBlank(message = "Это поле не должно быть пустым")
    private MeterType type;

    @Column(name = "value")
    @NotBlank(message = "Это поле не должно быть пустым")
    private float value;

    @Column(name = "is_approved")
    private boolean isApproved;
        
    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "user_id")      
    private UserJpaEntity user;

    
    public MeterType getType() {
        return type;
    }

    public void setType(MeterType type) {
        type = type;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        value = value;
    }

    public boolean isIsApproved() {
        return isApproved;
    }

    public void setIsApproved(boolean isApproved) {
        isApproved = isApproved;
    }

    public UserJpaEntity getUser() {
        return user;
    }

    public void setUser(UserJpaEntity user) {
        this.user = user;
    }



}