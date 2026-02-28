package Dom.project.Domain_layer.exception;

/**
 * Исключение выбрасывается при невалидных операциях с пользователем
 */
public class InvalidUserException extends RuntimeException {


    public InvalidUserException(String message) {
        super(message );
    }


    public static InvalidUserException userCannotBeNull() {
        return new InvalidUserException("User cannot be null");
    }

    public static InvalidUserException phoneCannotBeEmpty() {
        return new InvalidUserException("Phone number cannot be empty");
    }

    public static InvalidUserException invalidPhoneFormat(String phone) {
        return new InvalidUserException("Invalid phone format: " + phone);
    }

    public static InvalidUserException emailCannotBeEmpty() {
        return new InvalidUserException("Email cannot be empty");
    }

    public static InvalidUserException invalidEmailFormat(String email) {
        return new InvalidUserException("Invalid email format: " + email);
    }

    public static InvalidUserException passwordCannotBeEmpty() {
        return new InvalidUserException("Password cannot be empty");
    }

    public static InvalidUserException passwordTooShort(int minLength) {
        return new InvalidUserException("Password must be at least " + minLength + " characters");
    }

    public static InvalidUserException nameCannotBeEmpty() {
        return new InvalidUserException("Name cannot be empty");
    }

    public static InvalidUserException lastNameCannotBeEmpty() {
        return new InvalidUserException("Last name cannot be empty");
    }

    public static InvalidUserException invalidNameFormat(String name) {
        return new InvalidUserException("Invalid name format: " + name);
    }

    public static InvalidUserException incorrectPassword() {
        return new InvalidUserException("Incorrect password");
    }

    public static InvalidUserException userNotFound(Long id) {
        return new InvalidUserException("User not found with id: " + id);
    }

    public static InvalidUserException userNotFoundByPhone(String phone) {
        return new InvalidUserException("User not found with phone: " + phone);
    }

    public static InvalidUserException userNotFoundByEmail(String email) {
        return new InvalidUserException("User not found with email: " + email);
    }

    public static InvalidUserException userAlreadyExists(String phone, String email) {
        return new InvalidUserException("User already exists with phone: " + phone + " or email: " + email);
    }

    public static InvalidUserException alreadyHasCompany() {
        return new InvalidUserException("User already has a company assigned");
    }

    public static InvalidUserException noCompanyToRemove() {
        return new InvalidUserException("User has no company to remove");
    }

    public static InvalidUserException alreadyHasCounter() {
        return new InvalidUserException("User already has a counter assigned");
    }

    public static InvalidUserException alreadyHasLodger() {
        return new InvalidUserException("User already has a lodger assigned");
    }

    public static InvalidUserException alreadyHasMember() {
        return new InvalidUserException("User already has a member assigned");
    }
}