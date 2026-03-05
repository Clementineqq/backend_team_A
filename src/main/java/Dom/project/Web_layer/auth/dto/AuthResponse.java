package Dom.project.Web_layer.auth.dto;
//хз, надо ли оно все, мб уберу
public class AuthResponse {
    private String message;
    private String token; // потом JWT

    public AuthResponse(String message, String token) {
        this.message = message;
        this.token = token;
    }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}