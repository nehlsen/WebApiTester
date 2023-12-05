package me.nehlsen.webapitester.api.task;

import me.nehlsen.webapitester.api.assertion.AssertionDtoFactory;
import me.nehlsen.webapitester.persistence.task.HttpGetTaskEntity;
import me.nehlsen.webapitester.persistence.task.HttpPostTaskEntity;
import me.nehlsen.webapitester.persistence.task.HttpTaskEntity;
import me.nehlsen.webapitester.persistence.task.TaskEntity;
import me.nehlsen.webapitester.persistence.task.TaskEntityFactory;
import me.nehlsen.webapitester.persistence.task.UnknownTaskTypeException;
import me.nehlsen.webapitester.persistence.task.VoidTaskEntity;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.Objects;

@Component
public class TaskDtoFactory {

    private final AssertionDtoFactory assertionDtoFactory;

    public TaskDtoFactory(AssertionDtoFactory assertionDtoFactory) {
        this.assertionDtoFactory = assertionDtoFactory;
    }

    public TaskDto fromEntity(TaskEntity task) {
        Objects.requireNonNull(task, "TaskDtoFactory::fromEntity: requires non null TaskEntity");

        final TaskDto taskDto = new TaskDto(
                task.getUuid().toString(),
                taskType(task),
                task.getName(),
                task.getParameters(),
                task.getAssertions().stream().map(assertionDtoFactory::fromEntity).toList()
        );

        if (task instanceof HttpTaskEntity httpTask) {
            return taskDto
                    .withUri(Objects.requireNonNullElse(httpTask.getUri(), URI.create("")).toString())
                    .withHeaders(httpTask.getHeaders());
        }

        return taskDto;
    }

    private String taskType(TaskEntity task) {
        if (task instanceof VoidTaskEntity) {
            return TaskEntityFactory.TASK_TYPE_VOID;
        }
        if (task instanceof HttpGetTaskEntity) {
            return TaskEntityFactory.TASK_TYPE_HTTP_GET;
        }
        if (task instanceof HttpPostTaskEntity) {
            return TaskEntityFactory.TASK_TYPE_HTTP_POST;
        }

        throw UnknownTaskTypeException.ofTypeEntity(task);
    }
}
