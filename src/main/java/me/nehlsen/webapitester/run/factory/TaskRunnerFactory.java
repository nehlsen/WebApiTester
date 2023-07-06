package me.nehlsen.webapitester.run.factory;

import me.nehlsen.webapitester.run.TaskRunner;
import me.nehlsen.webapitester.run.context.PlanExecutionContext;
import me.nehlsen.webapitester.run.context.TaskExecutionContextFactory;
import me.nehlsen.webapitester.run.executor.TaskExecutor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskRunnerFactory {

    private final List<TaskExecutor> taskExecutors;
    private final TaskExecutionContextFactory taskExecutionContextFactory;

    public TaskRunnerFactory(List<TaskExecutor> taskExecutors, TaskExecutionContextFactory taskExecutionContextFactory) {
        this.taskExecutors = taskExecutors;
        this.taskExecutionContextFactory = taskExecutionContextFactory;
    }

    public TaskRunner create(PlanExecutionContext planExecutionContext) {
        return new TaskRunner(
                taskExecutors,
                planExecutionContext,
                taskExecutionContextFactory
        );
    }
}
