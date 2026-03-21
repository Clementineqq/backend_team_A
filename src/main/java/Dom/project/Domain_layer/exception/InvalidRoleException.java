package Dom.project.Domain_layer.exception;

public class InvalidRoleException extends DomainException {
    private static final String ERROR_CODE = "ROLE_EXCEPTION";

    public InvalidRoleException(String message) {
        super(message, ERROR_CODE);
    }

    public static InvalidRoleException roleCantBeNull(){
        throw new InvalidRoleException("Role cant be null");
    }
}