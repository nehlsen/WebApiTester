package me.nehlsen.webapitester.api.task;

import me.nehlsen.webapitester.api.assertion.AssertionDtoFactory;
import me.nehlsen.webapitester.fixture.TaskEntityFixture;
import me.nehlsen.webapitester.persistence.task.HttpGetTaskEntity;
import me.nehlsen.webapitester.persistence.task.HttpPostTaskEntity;
import me.nehlsen.webapitester.persistence.task.TaskEntity;
import me.nehlsen.webapitester.persistence.task.UnknownTaskTypeException;
import me.nehlsen.webapitester.persistence.task.VoidTaskEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TaskDtoFactoryTest {

    private TaskDtoFactory taskDtoFactory;

    @BeforeEach
    void setUp() {
        final AssertionDtoFactory assertionDtoFactory = Mockito.mock(AssertionDtoFactory.class);
        Mockito.verifyNoInteractions(assertionDtoFactory);

        taskDtoFactory = new TaskDtoFactory(assertionDtoFactory);
    }

    @Test
    public void create_from_void_task_entity() {
        VoidTaskEntity voidTaskEntity = TaskEntityFixture.voidTaskEntityWithoutAssertions();

        final TaskDto taskDto = taskDtoFactory.fromEntity(voidTaskEntity);
        assertThat(taskDto.getUuid()).isEqualTo(voidTaskEntity.getUuid().toString());
        assertThat(taskDto.getName()).isEqualTo(voidTaskEntity.getName());
        assertThat(taskDto.getType()).isEqualTo("void");
    }

    @Test
    public void create_from_http_get_task_entity() {
        final HttpGetTaskEntity httpGetTaskEntity = TaskEntityFixture.httpGetTaskEntityWithoutAssertions();

        final TaskDto taskDto = taskDtoFactory.fromEntity(httpGetTaskEntity);
        assertThat(taskDto.getUuid()).isEqualTo(httpGetTaskEntity.getUuid().toString());
        assertThat(taskDto.getName()).isEqualTo(httpGetTaskEntity.getName());
        assertThat(taskDto.getUri()).isEqualTo(httpGetTaskEntity.getUri().toString());
        assertThat(taskDto.getType()).isEqualTo("http_get");
    }

    @Test
    public void create_from_http_post_task_entity() {
        final HttpPostTaskEntity httpPostTaskEntity = TaskEntityFixture.httpPostTaskEntityWithoutAssertions();

        final TaskDto taskDto = taskDtoFactory.fromEntity(httpPostTaskEntity);
        assertThat(taskDto.getUuid()).isEqualTo(httpPostTaskEntity.getUuid().toString());
        assertThat(taskDto.getName()).isEqualTo(httpPostTaskEntity.getName());
        assertThat(taskDto.getUri()).isEqualTo(httpPostTaskEntity.getUri().toString());
        assertThat(taskDto.getType()).isEqualTo("http_post");
        assertThat(taskDto.getParameters()).isEqualTo(httpPostTaskEntity.getParameters());
        assertThat(taskDto.getHeaders()).isEqualTo(httpPostTaskEntity.getHeaders());
    }

    @Test
    public void create_from_unknown_task_entity() {
        UUID mockUuid = Mockito.mock(UUID.class);
        Mockito.when(mockUuid.toString()).thenReturn("faked mock UUID");
        TaskEntity taskEntity = Mockito.mock(TaskEntity.class);
        Mockito.when(taskEntity.getUuid()).thenReturn(mockUuid);

        final UnknownTaskTypeException unknownTaskTypeException = assertThrows(UnknownTaskTypeException.class, () -> taskDtoFactory.fromEntity(taskEntity));
        assertThat(unknownTaskTypeException).hasMessageContaining("Task Type not supported");
    }
}
