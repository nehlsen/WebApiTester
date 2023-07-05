package me.nehlsen.webapitester.api;

import me.nehlsen.webapitester.persistence.HttpGetTaskEntity;
import me.nehlsen.webapitester.persistence.TaskEntity;
import me.nehlsen.webapitester.persistence.VoidTaskEntity;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class TaskDtoFactoryTest {
    @Test
    public void create_from_void_task_entity() {
        final TaskDtoFactory taskDtoFactory = new TaskDtoFactory();

        VoidTaskEntity voidTaskEntity = new VoidTaskEntity();
        voidTaskEntity.setUuid(UUID.fromString("af70b1d3-ad41-4d9f-b44a-f3cc7524d142"));
        voidTaskEntity.setName("some void task");
        voidTaskEntity.setUri(URI.create("scheme://host/path"));

        final TaskDto taskDto = taskDtoFactory.fromEntity(voidTaskEntity);
        assertThat(taskDto.getUuid()).isEqualTo(voidTaskEntity.getUuid().toString());
        assertThat(taskDto.getName()).isEqualTo(voidTaskEntity.getName());
        assertThat(taskDto.getUri()).isEqualTo(voidTaskEntity.getUri().toString());
        assertThat(taskDto.getType()).isEqualTo("void");
    }

    @Test
    public void create_from_http_get_task_entity() {
        final TaskDtoFactory taskDtoFactory = new TaskDtoFactory();

        HttpGetTaskEntity httpGetTaskEntity = new HttpGetTaskEntity();
        httpGetTaskEntity.setUuid(UUID.fromString("af70b1d3-ad41-4d9f-b44a-f3cc7524d142"));
        httpGetTaskEntity.setName("some http get task");
        httpGetTaskEntity.setUri(URI.create("scheme://host/path"));

        final TaskDto taskDto = taskDtoFactory.fromEntity(httpGetTaskEntity);
        assertThat(taskDto.getUuid()).isEqualTo(httpGetTaskEntity.getUuid().toString());
        assertThat(taskDto.getName()).isEqualTo(httpGetTaskEntity.getName());
        assertThat(taskDto.getUri()).isEqualTo(httpGetTaskEntity.getUri().toString());
        assertThat(taskDto.getType()).isEqualTo("http_get");
    }
}
