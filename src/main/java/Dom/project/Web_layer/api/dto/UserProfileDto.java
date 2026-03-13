package Dom.project.Web_layer.api.dto;

import org.springframework.security.crypto.codec.Hex;
import java.time.LocalDateTime;
import java.util.List;

public class UserProfileDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private String phone;
    private String password;
    private String address;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<UserCountersDto> userCounters;
    private List<UserRequestDto> userRequests;

    public UserProfileDto() {}

    public UserProfileDto(Long id, String firstName, String lastName, String email, String phone) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
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

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public List<UserCountersDto> getUserCounters() { return userCounters; }
    public void setUserCounters(List<UserCountersDto> userCounters) { this.userCounters = userCounters; }

    public List<UserRequestDto> getUserRequests() { return userRequests; }
    public void setUserRequests(List<UserRequestDto> userRequests) { this.userRequests = userRequests; }
}