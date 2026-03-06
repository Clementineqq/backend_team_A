package Dom.project.Domain_layer.exception;

public class InvalidAddressException extends DomainException {

    private static final String ERROR_CODE = "INVALID_ADDRESS";

    public InvalidAddressException(String message) {
        super(message, ERROR_CODE);
    }

    public InvalidAddressException(String message, Throwable cause) {
        super(message, ERROR_CODE, cause);
    }

    public static InvalidAddressException streetCannotBeEmpty() {
        return new InvalidAddressException("Street cannot be empty");
    }

    public static InvalidAddressException houseCannotBeEmpty() {
        return new InvalidAddressException("House number cannot be empty");
    }

    public static InvalidAddressException addressCannotBeNull() {
        return new InvalidAddressException("Address cannot be null");
    }

    public static InvalidAddressException cityCannotBeEmpty() {
        return new InvalidAddressException("City cannot be empty");
    }

    public static InvalidAddressException invalidCityLength() {
        return new InvalidAddressException("City name must be between 2 and 100 characters");
    }

    public static InvalidAddressException invalidRegionLength() {
        return new InvalidAddressException("Region name must be between 2 and 100 characters");
    }

    public static InvalidAddressException invalidStreetLength() {
        return new InvalidAddressException("Street name must be between 2 and 100 characters");
    }

    public static InvalidAddressException invalidHouseLength() {
        return new InvalidAddressException("House number must be between 1 and 20 characters");
    }

    public static InvalidAddressException invalidFlatLength() {
        return new InvalidAddressException("Flat number must not exceed 10 characters");
    }

    public static InvalidAddressException invalidAreaFormat() {
        return new InvalidAddressException("Total area must be a valid number");
    }

    public static InvalidAddressException addressNotFound(Long id) {
        return new InvalidAddressException("Address not found with id: " + id);
    }
}