package Dom.project.Web_layer.api.dto;

import org.springframework.security.crypto.codec.Hex;

import java.time.LocalDateTime;
import java.util.List;

public class UserProfileDto {
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private String phone;
    private Hex password;
    private String address;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<UserCountersDto> userCounters;


    public UserProfileDto() {}

    public UserProfileDto(Long id, String username, String email, String firstName,
                          String lastName, String phone, String avatarUrl, LocalDateTime registrationDate) {

        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;

    }

    // Геттеры и сеттеры


    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }


}