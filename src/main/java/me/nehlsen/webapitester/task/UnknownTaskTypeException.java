package me.nehlsen.webapitester.task;

public class UnknownTaskTypeException extends RuntimeException {
    public UnknownTaskTypeException(String message) {
        super(message);
    }

    public static UnknownTaskTypeException ofTypeString(String taskType) {
        return new UnknownTaskTypeException(String.format(
                "Task Type \"%s\" not found",
                taskType
        ));
    }
}
