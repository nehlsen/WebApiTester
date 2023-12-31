package me.nehlsen.webapitester.run.context;

import lombok.Getter;
import lombok.Setter;
import me.nehlsen.webapitester.run.assertion.AssertionResult;
import me.nehlsen.webapitester.run.dto.AssertionDto;
import me.nehlsen.webapitester.run.dto.HttpRequestDto;
import me.nehlsen.webapitester.run.dto.HttpResponseDto;
import me.nehlsen.webapitester.run.dto.TaskDto;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter @Setter
public class TaskExecutionContext {

    public enum RequestFailedReason {
        NotFailed,
        HttpTimeout,
        OtherReason,
    }

    private final UUID uuid = UUID.randomUUID();
    private final PlanExecutionContext planExecutionContext;
    private final TaskExecutionContext previous;

    private final TaskDto task;
    private HttpRequestDto request;
    private RequestFailedReason requestFailed = RequestFailedReason.NotFailed;
    private HttpResponseDto response;

    private Map<AssertionDto, List<AssertionResult>> assertionResults = new HashMap<>();

    public TaskExecutionContext(TaskDto task, PlanExecutionContext planExecutionContext, TaskExecutionContext previousContext) {
        this.task = task;
        this.planExecutionContext = planExecutionContext;
        this.previous = previousContext;
    }

    public void addAssertionResults(AssertionDto assertion, List<AssertionResult> results) {
        assertionResults.put(assertion, results);
    }

    public boolean isResultPositive() {
        return assertionResults
                .values()
                .stream()
                .flatMap(Collection::stream)
                .map(AssertionResult::isPositive)
                .filter(positive -> !positive)
                .findFirst()
                .orElse(true);
    }
}
