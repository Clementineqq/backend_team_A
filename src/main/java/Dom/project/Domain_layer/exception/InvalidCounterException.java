package Dom.project.Domain_layer.exception;

import Dom.project.Domain_layer.enums.MeterType;

public class InvalidCounterException extends DomainException {

    private static final String ERROR_CODE = "INVALID_COUNTER";

    public InvalidCounterException(String message) {
        super(message, ERROR_CODE);
    }

    public InvalidCounterException(String message, Throwable cause) {
        super(message, ERROR_CODE, cause);
    }

    public InvalidCounterException(String message, String field, Object invalidValue) {
        super(message, ERROR_CODE, field, invalidValue);
    }

    public static InvalidCounterException nameCannotBeNull() {
        return new InvalidCounterException("Counter name cannot be null", "name", null);
    }

    public static InvalidCounterException valueCannotBeNull() {
        return new InvalidCounterException("Counter value cannot be null", "value", null);
    }

    public static InvalidCounterException valueCannotBeNegative(Double value) {
        return new InvalidCounterException("Counter value cannot be negative: " + value, "value", value);
    }

    public static InvalidCounterException valueCannotDecrease(Double oldValue, Double newValue) {
        return new InvalidCounterException(
                String.format("Counter value cannot decrease from %.2f to %.2f", oldValue, newValue),
                "value", newValue);
    }

    public static InvalidCounterException approvedCannotBeNull() {
        return new InvalidCounterException("Approved status cannot be null", "isApproved", null);
    }

    public static InvalidCounterException alreadyApproved() {
        return new InvalidCounterException("Counter is already approved", "isApproved", true);
    }

    public static InvalidCounterException notApproved() {
        return new InvalidCounterException("Counter is not approved", "isApproved", false);
    }

    public static InvalidCounterException cannotApproveWithoutValue() {
        return new InvalidCounterException("Cannot approve counter without value", "value", null);
    }

    public static InvalidCounterException counterNotFound(Long id) {
        return new InvalidCounterException("Counter not found with id: " + id, "id", id);
    }

    public static InvalidCounterException counterTypeAlreadyExists(MeterType type) {
        return new InvalidCounterException("Counter with type " + type + " already exists for this user", "name", type);
    }

    public static InvalidCounterException cannotModifyApproved() {
        return new InvalidCounterException("Cannot modify approved counter", null, null);
    }

    public static InvalidCounterException ownerCannotBeNull() {
        return new InvalidCounterException("Counter owner cannot be null", "owner", null);
    }

    public static InvalidCounterException userAlreadyHasCounterOfType(MeterType type) {
        return new InvalidCounterException("User already has a counter of type: " + type, "name", type);
    }
}