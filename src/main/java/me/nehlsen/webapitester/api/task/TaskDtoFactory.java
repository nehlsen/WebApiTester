package me.nehlsen.webapitester.api.task;

import me.nehlsen.webapitester.api.assertion.AssertionDtoFactory;
import me.nehlsen.webapitester.persistence.task.HttpGetTaskEntity;
import me.nehlsen.webapitester.persistence.task.HttpPostTaskEntity;
import me.nehlsen.webapitester.persistence.task.TaskEntity;
import me.nehlsen.webapitester.persistence.task.TaskEntityFactory;
import me.nehlsen.webapitester.persistence.task.VoidTaskEntity;
import me.nehlsen.webapitester.persistence.task.UnknownTaskTypeException;
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

        return new TaskDto(
                task.getUuid().toString(),
                taskType(task),
                task.getName(),
                Objects.requireNonNullElse(task.getUri(), URI.create("")).toString(),
                task.getAssertions().stream().map(assertionDtoFactory::fromEntity).toList()
        );
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
