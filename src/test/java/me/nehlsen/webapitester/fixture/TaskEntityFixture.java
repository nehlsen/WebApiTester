package me.nehlsen.webapitester.fixture;

import me.nehlsen.webapitester.persistence.task.HttpGetTaskEntity;
import me.nehlsen.webapitester.persistence.task.HttpPostTaskEntity;
import me.nehlsen.webapitester.persistence.task.VoidTaskEntity;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TaskEntityFixture {
    public static VoidTaskEntity voidTaskEntityWithoutAssertions() {
        VoidTaskEntity voidTaskEntity = new VoidTaskEntity();
        voidTaskEntity.setUuid(UUID.fromString("af70b1d3-ad41-4d9f-b44a-f3cc7524d142"));
        voidTaskEntity.setName("some void task");
        voidTaskEntity.setAssertions(List.of());

        return voidTaskEntity;
    }

    public static HttpGetTaskEntity httpGetTaskEntityWithoutAssertions() {
        HttpGetTaskEntity httpGetTaskEntity = new HttpGetTaskEntity();
        httpGetTaskEntity.setUuid(UUID.fromString("af70b1d3-ad41-4d9f-b44a-f3cc7524d142"));
        httpGetTaskEntity.setName("some http get task");
        httpGetTaskEntity.setUri(URI.create("scheme://host/path"));
        httpGetTaskEntity.setAssertions(List.of());

        return httpGetTaskEntity;
    }

    public static HttpPostTaskEntity httpPostTaskEntityWithoutAssertions() {
        HttpPostTaskEntity httpPostTaskEntity = new HttpPostTaskEntity();
        httpPostTaskEntity.setUuid(UUID.fromString("af70b1d3-ad41-4d9f-b44a-f3cc7524d142"));
        httpPostTaskEntity.setName("some http post task");
        httpPostTaskEntity.setUri(URI.create("scheme://host/path"));
        httpPostTaskEntity.setParameters(Map.of("body", "some data to post"));
        httpPostTaskEntity.setHeaders(Map.of("Content-Type", List.of("text/plain")));
        httpPostTaskEntity.setAssertions(List.of());

        return httpPostTaskEntity;
    }
}
