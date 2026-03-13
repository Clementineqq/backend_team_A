package Dom.project.Domain_layer.model;

import Dom.project.Domain_layer.enums.UserRole;
import Dom.project.Domain_layer.exception.InvalidRoleException;

import java.util.Date;
import java.util.Objects;

public class Role {
    private Long id;
    private Date createdAt;
    private Date updatedAt;
    private UserRole roleName;


    // Конструкторы
    public Role() {
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    public Role(UserRole roleName) {
        this();
        setRoleName(roleName);
    }

    public Role(Long id, UserRole roleName) {
        this(roleName);
        this.id = id;
    }

    //  Сетеры
    public void setId(Long id) { this.id = id; }

    public void setCreatedAt(Date createdAt) {
        if (createdAt == null) {
            throw new IllegalArgumentException("Created date cannot be null");
        }
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt != null ? updatedAt : new Date();
    }

    public void setRoleName(UserRole roleName) {
        if (roleName == null) {
            throw new InvalidRoleException("Role name cannot be null");
        }
        this.roleName = roleName;
        setUpdatedAt(new Date());
    }


    // Гетеры
    public Long getId() { return id; }

    public Date getCreatedAt() { return createdAt; }

    public Date getUpdatedAt() { return updatedAt; }

    public UserRole getRoleName() { return roleName; }

    //Бизнес-логика

    /*  Если понадобиться
    public int getRolePriority() {

    }
    */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id) && roleName == role.roleName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roleName);
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", roleName=" + roleName +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

}

