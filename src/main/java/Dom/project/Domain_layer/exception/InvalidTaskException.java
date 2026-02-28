package Dom.project.Domain_layer.exception;

import Dom.project.Domain_layer.enums.RequestStatus;

public class InvalidTaskException extends RuntimeException {



    public InvalidTaskException(String message) {
        super(message);
    }



    public static InvalidTaskException taskCannotBeNull() {
        return new InvalidTaskException("Task cannot be null");
    }

    public static InvalidTaskException titleCannotBeEmpty() {
        return new InvalidTaskException("Task title cannot be empty");
    }

    public static InvalidTaskException invalidTitleLength() {
        return new InvalidTaskException("Task title must be between 3 and 255 characters");
    }

    public static InvalidTaskException statusCannotBeNull() {
        return new InvalidTaskException("Task status cannot be null");
    }


    public static InvalidTaskException cannotAssignToCreator() {
        return new InvalidTaskException("Cannot assign task to its creator");
    }

    public static InvalidTaskException noAssignee() {
        return new InvalidTaskException("Task has no assignee");
    }

    public static InvalidTaskException taskNotFound(Long id) {
        return new InvalidTaskException("Task not found with id: " + id);
    }

    public static InvalidTaskException alreadyCompleted() {
        return new InvalidTaskException("Task is already completed");
    }

    public static InvalidTaskException alreadyCancelled() {
        return new InvalidTaskException("Task is already cancelled");
    }

    public static InvalidTaskException alreadyRejected() {
        return new InvalidTaskException("Task is already rejected");
    }
}