package Dom.project.Domain_layer.exception;

public class InvalidAddressException extends RuntimeException {

    private static final String ERROR_CODE = "INVALID_ADDRESS";

    public InvalidAddressException(String message) {
        super(message);
    }

    public static InvalidAddressException houseCannotBeEmpty() {
        return new InvalidAddressException("House cannot be null");
    }

    public static InvalidAddressException addressCannotBeNull() {
        return new InvalidAddressException("Address cannot be null");
    }

    public static InvalidAddressException streetCannotBeEmpty() {
        return new InvalidAddressException("Street cannot be empty");
    }

}