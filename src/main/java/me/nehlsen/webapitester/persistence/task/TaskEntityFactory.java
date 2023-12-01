package me.nehlsen.webapitester.persistence.task;

import me.nehlsen.webapitester.api.task.CreateTaskDto;
import me.nehlsen.webapitester.persistence.assertion.AssertionEntityFactory;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.Objects;

@Component
public class TaskEntityFactory {

    public static final String TASK_TYPE_VOID = "void";
    public static final String TASK_TYPE_HTTP_GET = "http_get";
    public static final String TASK_TYPE_HTTP_POST = "http_post";

    private final AssertionEntityFactory assertionEntityFactory;

    public TaskEntityFactory(AssertionEntityFactory assertionEntityFactory) {
        this.assertionEntityFactory = assertionEntityFactory;
    }

    public TaskEntity newTask(CreateTaskDto taskDto) {
        Objects.requireNonNull(taskDto, "TaskEntityFactory::newTask: requires non null TaskDto");

        final String taskType = taskDto.getType();

        return switch (taskType) {
            case TASK_TYPE_VOID -> createVoidTaskEntity(taskDto);
            case TASK_TYPE_HTTP_GET -> createHttpGetTaskEntity(taskDto);
            case TASK_TYPE_HTTP_POST -> createHttpPostTaskEntity(taskDto);
            default -> throw UnknownTaskTypeException.ofTypeString(taskType);
        };
    }

    private VoidTaskEntity createVoidTaskEntity(CreateTaskDto taskDto) {
        VoidTaskEntity voidTaskEntity = new VoidTaskEntity();
        setCommonProperties(taskDto, voidTaskEntity);

        return voidTaskEntity;
    }

    private HttpGetTaskEntity createHttpGetTaskEntity(CreateTaskDto taskDto) {
        HttpGetTaskEntity httpGetTaskEntity = new HttpGetTaskEntity();
        setCommonProperties(taskDto, httpGetTaskEntity);
        setHttpProperties(taskDto, httpGetTaskEntity);

        return httpGetTaskEntity;
    }

    private HttpPostTaskEntity createHttpPostTaskEntity(CreateTaskDto taskDto) {
        HttpPostTaskEntity httpPostTaskEntity = new HttpPostTaskEntity();
        setCommonProperties(taskDto, httpPostTaskEntity);
        setHttpProperties(taskDto, httpPostTaskEntity);

        return httpPostTaskEntity;
    }

    private void setCommonProperties(CreateTaskDto taskDto, TaskEntity taskEntity) {
        taskEntity.setName(taskDto.getName());
        taskEntity.setParameters(taskDto.getParameters());

        taskEntity.setAssertions(taskDto.getAssertions().stream().map(assertionEntityFactory::newAssertion).toList());
    }

    private void setHttpProperties(CreateTaskDto taskDto, HttpTaskEntity taskEntity) {
        taskEntity.setUri(URI.create(taskDto.getUri()));
        taskEntity.setHeaders(taskDto.getHeaders());
    }
}
