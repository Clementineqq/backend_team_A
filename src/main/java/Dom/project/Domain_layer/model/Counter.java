package Dom.project.Domain_layer.model;

import Dom.project.Domain_layer.enums.MeterType;
import Dom.project.Domain_layer.exception.InvalidCounterException;
import java.util.Date;
import java.util.Objects;

public class Counter {
    private Long id;
    private Date createdAt;
    private Date updatedAt;
    private MeterType name;
    private Double value;
    private Boolean isApproved;

    // Конструкторы
    public Counter() {
        this.createdAt = new Date();
        this.updatedAt = new Date();
        this.isApproved = false;
    }

    public Counter(MeterType name, Double value) {
        this();
        setName(name);
        setValue(value);
    }

    public Counter(Long id, MeterType name, Double value) {
        this(name, value);
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

    public void setName(MeterType name) {
        if (name == null) {
            throw InvalidCounterException.nameCannotBeNull();
        }
        this.name = name;
        setUpdatedAt();
    }

    public void setValue(Double value) {
        if (value == null) {
            throw InvalidCounterException.valueCannotBeNull();
        }
        if (value < 0) {
            throw InvalidCounterException.valueCannotBeNegative(value);
        }
        if (this.value != null && value < this.value) {
            throw InvalidCounterException.valueCannotDecrease(this.value, value);
        }
        this.value = value;
        this.isApproved = false; // Сбрасываем подтверждение при изменении значения
        setUpdatedAt();
    }

    public void setIsApproved(Boolean isApproved) {
        if (isApproved == null) {
            throw InvalidCounterException.approvedCannotBeNull();
        }
        this.isApproved = isApproved;
        setUpdatedAt();
    }

    // Геттеры
    public Long getId() { return id; }
    public Date getCreatedAt() { return createdAt; }
    public Date getUpdatedAt() { return updatedAt; }
    public MeterType getName() { return name; }
    public Double getValue() { return value; }
    public Boolean getIsApproved() { return isApproved; }


    public void approve() {
        if (this.isApproved) {
            throw InvalidCounterException.alreadyApproved();
        }
        if (value == null) {
            throw InvalidCounterException.cannotApproveWithoutValue();
        }
        this.isApproved = true;
        setUpdatedAt();
    }

    public void updateValue(Double newValue) {
        if (newValue == null) {
            throw InvalidCounterException.valueCannotBeNull();
        }
        if (newValue < 0) {
            throw InvalidCounterException.valueCannotBeNegative(newValue);
        }
        if (this.value != null && newValue < this.value) {
            throw InvalidCounterException.valueCannotDecrease(this.value, newValue);
        }
        this.value = newValue;
        this.isApproved = false; // Новые показания требуют подтверждения
        setUpdatedAt();
    }

    public Double getDifference(Double previousValue) {
        if (value == null || previousValue == null) return null;
        if (value < previousValue) {
            throw InvalidCounterException.valueCannotDecrease(previousValue, value);
        }
        return value - previousValue;
    }

    public void reset() {
        this.value = 0.0;
        this.isApproved = false;
        setUpdatedAt();
    }

    public void resetForNewPeriod() {
        this.value = 0.0;
        this.isApproved = true; // Новый период начинается с подтвержденного нуля
        setUpdatedAt();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Counter counter = (Counter) o;
        return Objects.equals(id, counter.id) ||
                (name == counter.name && Objects.equals(value, counter.value));
    }

    @Override
    public int hashCode() {
        return id != null ? Objects.hash(id) : Objects.hash(name, value);
    }

    @Override
    public String toString() {
        return "Counter{" +
                "id=" + id +
                ", name=" + getName() +
                ", value=" + value +
                ", isApproved=" + isApproved +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

}

