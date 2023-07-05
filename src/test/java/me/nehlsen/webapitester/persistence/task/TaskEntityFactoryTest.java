package me.nehlsen.webapitester.persistence.task;

import me.nehlsen.webapitester.api.task.CreateTaskDto;
import me.nehlsen.webapitester.persistence.task.assertion.AssertionEntityFactory;
import me.nehlsen.webapitester.task.UnknownTaskTypeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    public void create_void_task_with_name_and_uri_but_no_assertions() {
        final TaskEntityFactory taskEntityFactory = new TaskEntityFactory();

        final CreateTaskDto taskDto = new CreateTaskDto("void", "some task name", "needs://a-valid.url");

        final TaskEntity taskEntity = taskEntityFactory.newTask(taskDto);
        assertThat(taskEntity.getName()).isEqualTo(taskDto.getName());
        assertThat(taskEntity.getUri().toString()).isEqualTo(taskDto.getUri());
        assertThat(taskEntity).isInstanceOf(VoidTaskEntity.class);
    }

    @Test
    public void create_http_get_task_with_name_and_uri_but_no_assertions() {
        final TaskEntityFactory taskEntityFactory = new TaskEntityFactory();

        final CreateTaskDto taskDto = new CreateTaskDto("http_get", "another task name", "http://the-url.com");

        final TaskEntity taskEntity = taskEntityFactory.newTask(taskDto);
        assertThat(taskEntity.getName()).isEqualTo(taskDto.getName());
        assertThat(taskEntity.getUri().toString()).isEqualTo(taskDto.getUri());
        assertThat(taskEntity).isInstanceOf(HttpGetTaskEntity.class);
    }

    @Test
    public void create_unknown_task_type() {
        final TaskEntityFactory taskEntityFactory = new TaskEntityFactory();

        final CreateTaskDto taskDto = new CreateTaskDto("unknown_type", "another task name", "http://the-url.com");

        final UnknownTaskTypeException unknownTaskTypeException = assertThrows(UnknownTaskTypeException.class, () -> taskEntityFactory.newTask(taskDto));
        assertThat(unknownTaskTypeException).hasMessage("Task Type \"unknown_type\" not supported");
    }
}
