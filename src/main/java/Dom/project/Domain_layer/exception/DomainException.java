package Dom.project.Domain_layer.exception;

public class DomainException extends RuntimeException {

    private final String errorCode;
    private final String field;
    private final Object invalidValue;

    public DomainException(String message) {
        super(message);
        this.errorCode = "DOMAIN_ERROR";
        this.field = null;
        this.invalidValue = null;
    }

    public DomainException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
        this.field = null;
        this.invalidValue = null;
    }

    public DomainException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "DOMAIN_ERROR";
        this.field = null;
        this.invalidValue = null;
    }

    public DomainException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.field = null;
        this.invalidValue = null;
    }

    public DomainException(String message, String errorCode, String field, Object invalidValue) {
        super(message);
        this.errorCode = errorCode;
        this.field = field;
        this.invalidValue = invalidValue;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getField() {
        return field;
    }

    public Object getInvalidValue() {
        return invalidValue;
    }

    public boolean hasField() {
        return field != null && !field.isEmpty();
    }

    public boolean hasInvalidValue() {
        return invalidValue != null;
    }

    public String getLogMessage() {
        StringBuilder log = new StringBuilder(super.getMessage());

        if (hasField()) {
            log.append(" [field: ").append(field).append("]");
        }

        if (hasInvalidValue()) {
            log.append(" [invalid value: ").append(invalidValue).append("]");
        }

        log.append(" [error code: ").append(errorCode).append("]");

        return log.toString();
    }

    @Override
    public String toString() {
        return getLogMessage();
    }
}