package me.nehlsen.webapitester.persistence.task;

import me.nehlsen.webapitester.api.task.CreateTaskDto;
import me.nehlsen.webapitester.persistence.task.assertion.AssertionEntityFactory;
import me.nehlsen.webapitester.task.UnknownTaskTypeException;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.Objects;

@Component
public class TaskEntityFactory {

    public static final String TASK_TYPE_VOID = "void";
    public static final String TASK_TYPE_HTTP_GET = "http_get";

    private final AssertionEntityFactory assertionEntityFactory;

    public TaskEntityFactory(AssertionEntityFactory assertionEntityFactory) {
        this.assertionEntityFactory = assertionEntityFactory;
    }

    public TaskEntity newTask(CreateTaskDto taskDto) {
        Objects.requireNonNull(taskDto, "TaskEntityFactory::newTask: requires non null TaskDto");

        TaskEntity taskEntity;

        if (taskDto.getType().equals(TASK_TYPE_VOID)) {
            VoidTaskEntity voidTaskEntity = new VoidTaskEntity();
            taskEntity = voidTaskEntity;
        } else if (taskDto.getType().equals(TASK_TYPE_HTTP_GET)) {
            HttpGetTaskEntity httpGetTaskEntity = new HttpGetTaskEntity();
            taskEntity = httpGetTaskEntity;
        } else {
            throw UnknownTaskTypeException.ofTypeString(taskDto.getType());
        }

        taskEntity.setName(taskDto.getName());
        taskEntity.setUri(URI.create(taskDto.getUri()));

        taskEntity.setAssertions(taskDto.getAssertions().stream().map(assertionEntityFactory::newAssertion).toList());

        return taskEntity;
    }
}