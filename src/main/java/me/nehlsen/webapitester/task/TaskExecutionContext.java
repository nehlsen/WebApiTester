package me.nehlsen.webapitester.task;

import lombok.Getter;
import lombok.Setter;
import me.nehlsen.webapitester.task.assertion.CheckResult;
import me.nehlsen.webapitester.task.assertion.TaskAssertion;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskExecutionContext {

    @Getter
    Task task;

    @Getter @Setter
    HttpRequest request;
    @Getter @Setter
    private long requestTimeMillis = 0;

    @Getter @Setter
    HttpResponse<String> response;

    Map<TaskAssertion, List<CheckResult>> assertionResults = new HashMap<>();

    public TaskExecutionContext(Task task) {
        this.task = task;
    }

    public void addResults(TaskAssertion assertion, List<CheckResult> checkResults) {
        assertionResults.put(assertion, checkResults);
    }
}
