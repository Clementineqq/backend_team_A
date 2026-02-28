package Dom.project.Domain_layer.exception;

public class InvalidLodgerException extends RuntimeException{

    public InvalidLodgerException(String message) {
        super(message);
    }

    public static InvalidLodgerException lodgerCannotBeNull() {
        return new InvalidLodgerException("Lodger cannot be null");
    }


}
