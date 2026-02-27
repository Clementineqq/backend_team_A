package Dom.project.Infrastructure_layer.entity;

import Dom.project.Domain_layer.enums.MeterType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class MeterReadingJpaEntity extends Base_entity{
    
    private MeterType Type;
    private float Value;
    private boolean IsApproved;
        
    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "user_id")      
    private UserJpaEntity user;

    
    public MeterType getType() {
        return Type;
    }

    public void setType(MeterType type) {
        Type = type;
    }

    public float getValue() {
        return Value;
    }

    public void setValue(float value) {
        Value = value;
    }

    public boolean isIsApproved() {
        return IsApproved;
    }

    public void setIsApproved(boolean isApproved) {
        IsApproved = isApproved;
    }

    public UserJpaEntity getUser() {
        return user;
    }

    public void setUser(UserJpaEntity user) {
        this.user = user;
    }



}