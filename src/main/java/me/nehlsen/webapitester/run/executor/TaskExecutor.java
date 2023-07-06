package me.nehlsen.webapitester.run.executor;

import me.nehlsen.webapitester.run.context.TaskExecutionContext;
import me.nehlsen.webapitester.run.dto.TaskDto;

public interface TaskExecutor {
    boolean supports(TaskDto task);

    void execute(TaskExecutionContext context);
}
