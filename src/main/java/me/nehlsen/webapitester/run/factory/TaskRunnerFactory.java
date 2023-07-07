package me.nehlsen.webapitester.run.factory;

import me.nehlsen.webapitester.run.TaskRunner;
import me.nehlsen.webapitester.run.assertion.AssertionsChecker;
import me.nehlsen.webapitester.run.context.PlanExecutionContext;
import me.nehlsen.webapitester.run.context.TaskExecutionContextFactory;
import me.nehlsen.webapitester.run.executor.TaskExecutor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskRunnerFactory {

    private final List<TaskExecutor> taskExecutors;
    private final TaskExecutionContextFactory taskExecutionContextFactory;
    private final AssertionsChecker assertionsChecker;

    public TaskRunnerFactory(
            List<TaskExecutor> taskExecutors,
            TaskExecutionContextFactory taskExecutionContextFactory,
            AssertionsChecker assertionsChecker
    ) {
        this.taskExecutors = taskExecutors;
        this.taskExecutionContextFactory = taskExecutionContextFactory;
        this.assertionsChecker = assertionsChecker;
    }

    public TaskRunner create(PlanExecutionContext planExecutionContext) {
        return new TaskRunner(
                taskExecutors,
                planExecutionContext,
                taskExecutionContextFactory,
                assertionsChecker
        );
    }
}
