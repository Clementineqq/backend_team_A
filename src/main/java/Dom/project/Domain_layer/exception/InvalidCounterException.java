package Dom.project.Domain_layer.exception;

public class InvalidCounterException extends RuntimeException {


    public InvalidCounterException(String message) {
        super(message);
    }


    public static InvalidCounterException nameCannotBeNull() {
        return new InvalidCounterException("Counter name cannot be null");
    }

    public static InvalidCounterException valueCannotBeNull() {
        return new InvalidCounterException("Counter value cannot be null");
    }

    public static InvalidCounterException valueCannotBeNegative(Double value) {
        return new InvalidCounterException("Counter value cannot be negative: " + value);
    }

    public static InvalidCounterException valueCannotDecrease(Double oldValue, Double newValue) {
        return new InvalidCounterException(String.format(
                "Counter value cannot decrease from %.2f to %.2f", oldValue, newValue));
    }

    public static InvalidCounterException approvedCannotBeNull() {
        return new InvalidCounterException("Approved status cannot be null");
    }


    public static InvalidCounterException alreadyApproved() {
        return new InvalidCounterException("Counter is already approved");
    }

    public static InvalidCounterException notApproved() {
        return new InvalidCounterException("Counter is not approved");
    }

    public static InvalidCounterException cannotApproveWithoutValue() {
        return new InvalidCounterException("Cannot approve counter without value");
    }

    public static InvalidCounterException counterNotFound(Long id) {
        return new InvalidCounterException("Counter not found with id: " + id);
    }

    public static InvalidCounterException counterTypeAlreadyExists(String type) {
        return new InvalidCounterException("Counter with type " + type + " already exists");
    }
}