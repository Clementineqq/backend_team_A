package Dom.project.Infrastructure_layer.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

// Базовая сущность, поля которой наследуются всем остальным
// Тут ничего менять не нужно
@MappedSuperclass
public abstract class Base_entity {

    @Id
    @Column(name = "id", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @Column(name = "created_at", nullable = false, updatable = false) //[nio-8080-exec-3] org.hibernate.orm.jdbc.error             : ОШИБКА: столбец uje1_0.date_create не существует ---- поэтому исправил
    private LocalDateTime dateCreate;

    @Column(name = "updated_at")
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
