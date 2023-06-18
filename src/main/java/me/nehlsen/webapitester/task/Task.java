package me.nehlsen.webapitester.task;

import me.nehlsen.webapitester.task.assertion.TaskAssertion;

import java.net.URI;
import java.net.http.HttpRequest;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

public interface Task {
    int DEFAULT_REQUEST_TIMEOUT_SECONDS = 10;

    URI getUri();

    default HttpRequest createRequest(HttpRequest.Builder builder) {
        return builder.uri(getUri())
                .timeout(Duration.of(DEFAULT_REQUEST_TIMEOUT_SECONDS, ChronoUnit.SECONDS))
                .GET()
                .build();
    }

    List<TaskAssertion> getAssertions();
}
