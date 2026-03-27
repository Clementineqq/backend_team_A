package Dom.project.Web_layer.api.dto;

import Dom.project.Domain_layer.enums.UserRole;

import java.time.LocalDateTime;
import java.util.List;

public class WorkerDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private String phone;
    private String password;
    private UserRole role;
    private AddressDto address;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long companyId;
    private List<ServiceRequestDto> workerRequests;

    public WorkerDto() {

    }

    public WorkerDto(Long id, String firstName, String lastName, String middleName,
                     String email, String phone, String password, AddressDto address,
                     LocalDateTime createdAt, LocalDateTime updatedAt,
                     List<ServiceRequestDto> workerRequests) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.address = address;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.workerRequests = workerRequests;
    }

    // Геттеры и сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getMiddleName() { return middleName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public AddressDto getAddress() { return address; }
    public void setAddress(AddressDto address) { this.address = address; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public List<ServiceRequestDto> getWorkerRequests() { return workerRequests; }
    public void setWorkerRequests(List<ServiceRequestDto> workerRequests) { this.workerRequests = workerRequests; }

    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }

    public Long getCompanyId() {return companyId;}
    public void setCompanyId(Long companyId) {this.companyId = companyId;}
}