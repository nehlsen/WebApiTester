package me.nehlsen.webapitester.api;

import me.nehlsen.webapitester.persistence.HttpGetTaskEntity;
import me.nehlsen.webapitester.persistence.TaskEntity;
import me.nehlsen.webapitester.persistence.TaskEntityFactory;
import me.nehlsen.webapitester.persistence.VoidTaskEntity;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.Objects;

@Component
public class TaskDtoFactory {

    public TaskDto fromEntity(TaskEntity task) {
        return new TaskDto(
                task.getUuid().toString(),
                taskType(task),
                task.getName(),
                Objects.requireNonNullElse(task.getUri(), URI.create("")).toString()
        );
    }

    private String taskType(TaskEntity task) {
        if (task instanceof VoidTaskEntity) {
            return TaskEntityFactory.TASK_TYPE_VOID;
        }
        if (task instanceof HttpGetTaskEntity) {
            return TaskEntityFactory.TASK_TYPE_HTTP_GET;
        }

        throw new RuntimeException();
    }
}
