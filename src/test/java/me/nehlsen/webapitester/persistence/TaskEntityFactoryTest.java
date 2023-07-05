package me.nehlsen.webapitester.persistence;

import me.nehlsen.webapitester.api.CreateTaskDto;
import me.nehlsen.webapitester.api.TaskDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class TaskEntityFactoryTest {
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
}
