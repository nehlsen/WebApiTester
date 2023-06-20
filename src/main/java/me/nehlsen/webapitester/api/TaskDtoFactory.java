package me.nehlsen.webapitester.api;

import me.nehlsen.webapitester.task.HttpGetTask;
import me.nehlsen.webapitester.task.Task;
import me.nehlsen.webapitester.task.TaskFactory;
import me.nehlsen.webapitester.task.VoidTask;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.Objects;

@Component
public class TaskDtoFactory {
    public TaskDto fromTask(Task task) {
        return new TaskDto(
                taskType(task),
                task.getName(),
                Objects.requireNonNullElse(task.getUri(), URI.create("")).toString()
        );
    }

    private String taskType(Task task) {
        if (task instanceof VoidTask) {
            return TaskFactory.TASK_TYPE_VOID;
        }
        if (task instanceof HttpGetTask) {
            return TaskFactory.TASK_TYPE_HTTP_GET;
        }

        throw new RuntimeException();
    }
}
