package me.nehlsen.webapitester.run.executor;

import me.nehlsen.webapitester.persistence.task.TaskEntity;
import me.nehlsen.webapitester.run.context.TaskExecutionContext;

public interface TaskExecutor {
    boolean supports(TaskEntity task);

    void execute(TaskExecutionContext context);
}
