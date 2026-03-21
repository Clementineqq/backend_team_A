package Dom.project.Web_layer.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtil {
    
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    
    public static String hashPassword(String rawPassword) {
        return encoder.encode(rawPassword);
    }
    
    public static boolean verifyPassword(String rawPassword, String hashedPassword) {
        return encoder.matches(rawPassword, hashedPassword);
    }
    
    // для тестирования
    public static void main(String[] args) {
        String testPassword = "1488228";
        String hashed = hashPassword(testPassword);
        System.out.println("Original: " + testPassword);
        System.out.println("Hashed: " + hashed);
        System.out.println("Verify: " + verifyPassword(testPassword, hashed));
    }
}