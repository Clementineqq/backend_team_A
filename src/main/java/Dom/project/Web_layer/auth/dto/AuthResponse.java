package Dom.project.Web_layer.auth.dto;

public class AuthResponse {
    private String message;
    private String token;
    private String email;
    private String fullName;

    // Пустой конструктор (нужен для JSON)
    public AuthResponse() {}

    // Конструктор для ответа с сообщением и токеном
    public AuthResponse(String message, String token) {
        this.message = message;
        this.token = token;
    }

    // Конструктор для полного ответа (который ты используешь)
    public AuthResponse(String message, String token, String email, String fullName) {
        this.message = message;
        this.token = token;
        this.email = email;
        this.fullName = fullName;
    }

    // Геттеры и сеттеры
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
}