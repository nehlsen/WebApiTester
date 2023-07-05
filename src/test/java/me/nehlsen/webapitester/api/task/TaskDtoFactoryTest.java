package me.nehlsen.webapitester.api.task;

import me.nehlsen.webapitester.api.assertion.AssertionDtoFactory;
import me.nehlsen.webapitester.persistence.task.HttpGetTaskEntity;
import me.nehlsen.webapitester.persistence.task.TaskEntity;
import me.nehlsen.webapitester.persistence.task.VoidTaskEntity;
import me.nehlsen.webapitester.task.UnknownTaskTypeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.net.URI;
import java.util.List;
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
        VoidTaskEntity voidTaskEntity = new VoidTaskEntity();
        voidTaskEntity.setUuid(UUID.fromString("af70b1d3-ad41-4d9f-b44a-f3cc7524d142"));
        voidTaskEntity.setName("some void task");
        voidTaskEntity.setUri(URI.create("scheme://host/path"));
        voidTaskEntity.setAssertions(List.of());

        final TaskDto taskDto = taskDtoFactory.fromEntity(voidTaskEntity);
        assertThat(taskDto.getUuid()).isEqualTo(voidTaskEntity.getUuid().toString());
        assertThat(taskDto.getName()).isEqualTo(voidTaskEntity.getName());
        assertThat(taskDto.getUri()).isEqualTo(voidTaskEntity.getUri().toString());
        assertThat(taskDto.getType()).isEqualTo("void");
    }

    @Test
    public void create_from_http_get_task_entity() {
        HttpGetTaskEntity httpGetTaskEntity = new HttpGetTaskEntity();
        httpGetTaskEntity.setUuid(UUID.fromString("af70b1d3-ad41-4d9f-b44a-f3cc7524d142"));
        httpGetTaskEntity.setName("some http get task");
        httpGetTaskEntity.setUri(URI.create("scheme://host/path"));
        httpGetTaskEntity.setAssertions(List.of());

        final TaskDto taskDto = taskDtoFactory.fromEntity(httpGetTaskEntity);
        assertThat(taskDto.getUuid()).isEqualTo(httpGetTaskEntity.getUuid().toString());
        assertThat(taskDto.getName()).isEqualTo(httpGetTaskEntity.getName());
        assertThat(taskDto.getUri()).isEqualTo(httpGetTaskEntity.getUri().toString());
        assertThat(taskDto.getType()).isEqualTo("http_get");
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
