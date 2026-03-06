package Dom.project.Web_layer.api.dto;

import java.time.LocalDateTime;

public class WorkerDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String position;
    private String email;
    private String phone;
    private LocalDateTime hireDate;

    // Пустой конструктор
    public WorkerDto() {}

    // Конструктор со всеми полями
    public WorkerDto(Long id, String firstName, String lastName, String position,
                     String email, String phone, LocalDateTime hireDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.email = email;
        this.phone = phone;
        this.hireDate = hireDate;
    }

    // Геттеры и сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public LocalDateTime getHireDate() { return hireDate; }
    public void setHireDate(LocalDateTime hireDate) { this.hireDate = hireDate; }
}