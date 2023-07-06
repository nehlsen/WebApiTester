package me.nehlsen.webapitester.run.context;

import lombok.Getter;
import lombok.Setter;
import me.nehlsen.webapitester.run.dto.TaskDto;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Getter @Setter
public class TaskExecutionContext {
    private final PlanExecutionContext planExecutionContext;

    private final TaskDto task;
    private HttpRequest request;
    HttpResponse<String> response;

    public TaskExecutionContext(TaskDto task, PlanExecutionContext planExecutionContext) {
        this.task = task;
        this.planExecutionContext = planExecutionContext;
    }
}
