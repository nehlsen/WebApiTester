package me.nehlsen.webapitester.run.executor;

import me.nehlsen.webapitester.persistence.task.TaskEntity;
import me.nehlsen.webapitester.persistence.task.VoidTaskEntity;
import me.nehlsen.webapitester.run.context.TaskExecutionContext;

public class VoidTaskExecutor implements TaskExecutor {
    @Override
    public boolean supports(TaskEntity task) {
        return task instanceof VoidTaskEntity;
    }

    @Override
    public void execute(TaskExecutionContext context) {
        // NO-OP
    }
}
