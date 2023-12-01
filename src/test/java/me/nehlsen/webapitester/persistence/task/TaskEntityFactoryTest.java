package me.nehlsen.webapitester.persistence.task;

import me.nehlsen.webapitester.api.task.CreateTaskDto;
import me.nehlsen.webapitester.persistence.assertion.AssertionEntityFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TaskEntityFactoryTest {

    private TaskEntityFactory taskEntityFactory;

    @BeforeEach
    void setUp() {
        final AssertionEntityFactory assertionEntityFactory = Mockito.mock(AssertionEntityFactory.class);
        Mockito.verifyNoInteractions(assertionEntityFactory);

        taskEntityFactory = new TaskEntityFactory(assertionEntityFactory);
    }

    @Test
    public void create_void_task_with_name_but_no_assertions() {
        final CreateTaskDto taskDto = new CreateTaskDto("void", "some task name");

        final TaskEntity taskEntity = taskEntityFactory.newTask(taskDto);
        assertThat(taskEntity.getName()).isEqualTo(taskDto.getName());
        assertThat(taskEntity).isInstanceOf(VoidTaskEntity.class);
    }

    @Test
    public void create_http_get_task_with_name_and_uri_but_no_assertions() {
        final CreateTaskDto taskDto = new CreateTaskDto("http_get", "another task name", "http://the-url.com");

        final TaskEntity taskEntity = taskEntityFactory.newTask(taskDto);
        assertThat(taskEntity.getName()).isEqualTo(taskDto.getName());
        assertThat(taskEntity).isInstanceOf(HttpGetTaskEntity.class);

        assertThat(taskEntity).isInstanceOf(HttpTaskEntity.class);
        final HttpTaskEntity httpTaskEntity = (HttpTaskEntity) taskEntity;
        assertThat(httpTaskEntity.getUri().toString()).isEqualTo(taskDto.getUri());
    }

    @Test
    public void create_unknown_task_type() {
        final CreateTaskDto taskDto = new CreateTaskDto("unknown_type", "another task name", "http://the-url.com");

        final UnknownTaskTypeException unknownTaskTypeException = assertThrows(UnknownTaskTypeException.class, () -> taskEntityFactory.newTask(taskDto));
        assertThat(unknownTaskTypeException).hasMessage("Task Type \"unknown_type\" not supported");
    }

    @Test
    public void it_creates_http_post_task() {
        final CreateTaskDto taskDto = new CreateTaskDto("http_post", "post some data", "http://the-url.com")
                .withParameters(Map.of("body", "{some: \"data\", more: 16}"));

        final TaskEntity taskEntity = taskEntityFactory.newTask(taskDto);
        assertThat(taskEntity).isInstanceOf(HttpPostTaskEntity.class);
        assertThat(taskEntity.getName()).isEqualTo(taskDto.getName());
        assertThat(taskEntity.getParameters()).isEqualTo(taskDto.getParameters());

        assertThat(taskEntity).isInstanceOf(HttpTaskEntity.class);
        final HttpTaskEntity httpTaskEntity = (HttpTaskEntity) taskEntity;
        assertThat(httpTaskEntity.getUri().toString()).isEqualTo(taskDto.getUri());
    }
}
