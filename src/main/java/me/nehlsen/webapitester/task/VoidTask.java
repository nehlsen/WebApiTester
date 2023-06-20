package me.nehlsen.webapitester.task;

import me.nehlsen.webapitester.task.assertion.TaskAssertion;

import java.net.URI;
import java.util.List;

public class VoidTask implements Task {
    @Override
    public String getName() {
        return null;
    }

    @Override
    public URI getUri() {
        return null;
    }

    @Override
    public List<TaskAssertion> getAssertions() {
        return null;
    }
}
