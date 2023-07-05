package me.nehlsen.webapitester.task;

import me.nehlsen.webapitester.api.task.TaskDto;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@Component
public class TaskFactory {
    public static final String TASK_TYPE_VOID = "void";
    public static final String TASK_TYPE_HTTP_GET = "http_get";

    public Task fromDto(TaskDto taskDto) {
        final String taskType = Objects.requireNonNullElse(taskDto.getType(), "");
        if (taskType.equals(TaskFactory.TASK_TYPE_VOID)) {
            return new VoidTask();
        }
        if (taskType.equals(TaskFactory.TASK_TYPE_HTTP_GET)) {
            return new HttpGetTask(
                    taskDto.getName(),
                    URI.create(Objects.requireNonNullElse(taskDto.getUri(), "")),
                    List.of()
            );
        }

        throw UnknownTaskTypeException.ofTypeString(taskType);
    }
}
