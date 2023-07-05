package me.nehlsen.webapitester.task;

import me.nehlsen.webapitester.persistence.task.TaskEntity;

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

    public static UnknownTaskTypeException ofTypeEntity(TaskEntity task) {
        return new UnknownTaskTypeException(String.format(
                "Task Type not supported (\"%s\")",
                task.getClass()
        ));
    }
}
