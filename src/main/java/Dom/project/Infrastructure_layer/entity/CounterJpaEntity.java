package Dom.project.Infrastructure_layer.entity;

import Dom.project.Domain_layer.enums.MeterType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity()
@Table(name="counter")
public class CounterJpaEntity extends Base_entity{

    @ManyToOne
    @JoinColumn(name = "id_user")
    private UserJpaEntity user;

    @NotBlank(message = "Это поле не должно быть пустым")
    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private MeterType type;

    @Column(name = "counter_value")
    @NotBlank(message = "Это поле не должно быть пустым")
    private Double value;

    @Column(name = "is_approved")
    private boolean isApproved;
    
    public MeterType getType() {
        return type;
    }

    public void setType(MeterType type) {
        this.type = type;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public UserJpaEntity getUser() {
        return user;
    }

    public void setUser(UserJpaEntity user) {
        this.user = user;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setIsApproved(boolean isApproved) {
        this.isApproved = isApproved;
    }
}