package me.nehlsen.webapitester.task;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.nehlsen.webapitester.task.assertion.TaskAssertion;

import java.net.URI;
import java.util.List;

@AllArgsConstructor
public class HttpGetTask implements Task {

    @Getter
    URI uri;

    @Getter
    List<TaskAssertion> assertions;
}
