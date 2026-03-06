package Dom.project.Infrastructure_layer.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

// Базовая сущность, поля которой наследуются всем остальным
// Тут ничего менять не нужно
@MappedSuperclass
public abstract class Base_entity {

    @Id
    @Column(name = "id", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @Column(name = "date_create", nullable = false, updatable = false)
    private LocalDateTime dateCreate;

    @Column(name = "date_update")
    private LocalDateTime dateUpdate;

    @PrePersist
    protected void onCreate(){
        dateCreate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate(){
        dateUpdate = LocalDateTime.now();
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public LocalDateTime getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(LocalDateTime dateCreate) {
        this.dateCreate = dateCreate;
    }

    public LocalDateTime getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(LocalDateTime dateUpdate) {
        this.dateUpdate = dateUpdate;
    }
}
