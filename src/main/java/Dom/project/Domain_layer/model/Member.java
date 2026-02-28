package Dom.project.Domain_layer.model;

import Dom.project.Domain_layer.enums.UserRole;
import Dom.project.Domain_layer.exception.InvalidRoleException;
import java.util.Date;
import java.util.Objects;

public class Member {
    private Long id;
    private Date createdAt;
    private Date updatedAt;
    private Role role;


    // Конструкторы
    public Member() {
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    public Member(Role role) {
        this();
        setRole(role);
    }

    public Member(Long id, Role role) {
        this(role);
        this.id = id;
    }

    //сетеры
    public void setId(Long id) {
        this.id = id;
        setUpdatedAt(new Date());
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

    public void setRole(Role role) {
        if (role == null) {
            throw new InvalidRoleException("Role cannot be null");
        }
        this.role = role;
        setUpdatedAt(new Date());
    }


    //гетеры
    public Long getId() { return id; }

    public Date getCreatedAt() { return createdAt; }

    public Date getUpdatedAt() { return updatedAt; }

    public Role getRole() { return role; }

    // Бизнес-логика

    public UserRole getRoleName() {
        return role != null ? role.getRoleName() : null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(id, member.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", role=" + (role != null ? role.getRoleName() : "null") +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}

