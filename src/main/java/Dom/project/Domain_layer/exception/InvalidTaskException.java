package Dom.project.Domain_layer.exception;

import Dom.project.Domain_layer.enums.RequestStatus;

public class InvalidTaskException extends DomainException {

    private static final String ERROR_CODE = "INVALID_TASK";

    public InvalidTaskException(String message) {
        super(message, ERROR_CODE);
    }

    public InvalidTaskException(String message, Throwable cause) {
        super(message, ERROR_CODE, cause);
    }

    public InvalidTaskException(String message, String field, Object invalidValue) {
        super(message, ERROR_CODE, field, invalidValue);
    }

    public static InvalidTaskException taskCannotBeNull() {
        return new InvalidTaskException("Task cannot be null", "task", null);
    }

    public static InvalidTaskException titleCannotBeEmpty() {
        return new InvalidTaskException("Task title cannot be empty", "title", null);
    }

    public static InvalidTaskException invalidTitleLength() {
        return new InvalidTaskException("Task title must be between 3 and 255 characters", "title", null);
    }

    public static InvalidTaskException statusCannotBeNull() {
        return new InvalidTaskException("Task status cannot be null", "requestStatus", null);
    }

    public static InvalidTaskException invalidStatusTransition(RequestStatus from, RequestStatus to) {
        return new InvalidTaskException(
                String.format("Cannot transition task from %s to %s",
                        from != null ? from : "null",
                        to != null ? to : "null"),
                "requestStatus", to);
    }

    public static InvalidTaskException cannotAssignToCreator() {
        return new InvalidTaskException("Cannot assign task to its creator", "assignee", null);
    }

    public static InvalidTaskException noAssignee() {
        return new InvalidTaskException("Task has no assignee", "assignee", null);
    }

    public static InvalidTaskException taskNotFound(Long id) {
        return new InvalidTaskException("Task not found with id: " + id, "id", id);
    }

    public static InvalidTaskException alreadyCompleted() {
        return new InvalidTaskException("Task is already completed", "requestStatus", RequestStatus.Completed);
    }

    public static InvalidTaskException alreadyRejected() {
        return new InvalidTaskException("Task is already rejected", "requestStatus", RequestStatus.Rejected);
    }

    public static InvalidTaskException cannotModifyCompleted() {
        return new InvalidTaskException("Cannot modify completed task", null, null);
    }

    public static InvalidTaskException cannotModifyCancelled() {
        return new InvalidTaskException("Cannot modify cancelled task", null, null);
    }
}