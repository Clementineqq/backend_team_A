package Dom.project.Infrastructure_layer.entity;

import Dom.project.Domain_layer.enums.MeterType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity()
@Table(name="counter")
public class CounterJpaEntity extends Base_entity{

    @NotBlank(message = "Это поле не должно быть пустым")
    @Column(name = "name")
    private MeterType type;

    @Column(name = "value")
    @NotBlank(message = "Это поле не должно быть пустым")
    private double value;

    @Column(name = "is_approved")
    private boolean isApproved;
    
    public MeterType getType() {
        return type;
    }

    public void setType(MeterType type) {
        type = type;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        value = value;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setIsApproved(boolean isApproved) {
        isApproved = isApproved;
    }
}