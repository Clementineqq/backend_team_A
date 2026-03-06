package Dom.project.Domain_layer.exception;

public class InvalidUserException extends DomainException {

    private static final String ERROR_CODE = "INVALID_USER";

    public InvalidUserException(String message) {
        super(message, ERROR_CODE);
    }

    public InvalidUserException(String message, Throwable cause) {
        super(message, ERROR_CODE, cause);
    }

    public InvalidUserException(String message, String field, Object invalidValue) {
        super(message, ERROR_CODE, field, invalidValue);
    }

    public static InvalidUserException userCannotBeNull() {
        return new InvalidUserException("User cannot be null", "user", null);
    }

    public static InvalidUserException phoneCannotBeEmpty() {
        return new InvalidUserException("Phone number cannot be empty", "phone_number", null);
    }

    public static InvalidUserException invalidPhoneFormat(String phone) {
        return new InvalidUserException("Invalid phone format: " + phone, "phone_number", phone);
    }

    public static InvalidUserException emailCannotBeEmpty() {
        return new InvalidUserException("Email cannot be empty", "email", null);
    }

    public static InvalidUserException invalidEmailFormat(String email) {
        return new InvalidUserException("Invalid email format: " + email, "email", email);
    }

    public static InvalidUserException passwordCannotBeEmpty() {
        return new InvalidUserException("Password cannot be empty", "password", null);
    }

    public static InvalidUserException passwordTooShort(int minLength) {
        return new InvalidUserException("Password must be at least " + minLength + " characters", "password", null);
    }

    public static InvalidUserException nameCannotBeEmpty() {
        return new InvalidUserException("Name cannot be empty", "name", null);
    }

    public static InvalidUserException lastNameCannotBeEmpty() {
        return new InvalidUserException("Last name cannot be empty", "lastName", null);
    }

    public static InvalidUserException invalidNameLength() {
        return new InvalidUserException("Name must be between 2 and 50 characters", null, null);
    }

    public static InvalidUserException incorrectPassword() {
        return new InvalidUserException("Incorrect password", "password", null);
    }

    public static InvalidUserException userNotFound(Long id) {
        return new InvalidUserException("User not found with id: " + id, "id", id);
    }

    public static InvalidUserException userNotFoundByPhone(String phone) {
        return new InvalidUserException("User not found with phone: " + phone, "phone_number", phone);
    }

    public static InvalidUserException userNotFoundByEmail(String email) {
        return new InvalidUserException("User not found with email: " + email, "email", email);
    }

    public static InvalidUserException userAlreadyExists(String phone, String email) {
        return new InvalidUserException("User already exists with phone: " + phone + " or email: " + email, null, null);
    }

    public static InvalidUserException alreadyHasCompany() {
        return new InvalidUserException("User already has a company assigned", "company", null);
    }

    public static InvalidUserException noCompanyToRemove() {
        return new InvalidUserException("User has no company to remove", "company", null);
    }

    public static InvalidUserException alreadyHasCounter() {
        return new InvalidUserException("User already has a counter assigned", "counter", null);
    }

    public static InvalidUserException alreadyHasLodger() {
        return new InvalidUserException("User already has a lodger assigned", "lodger", null);
    }

    public static InvalidUserException alreadyHasMember() {
        return new InvalidUserException("User already has a member assigned", "member", null);
    }
}