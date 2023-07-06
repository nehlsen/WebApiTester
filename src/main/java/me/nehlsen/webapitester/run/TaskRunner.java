package me.nehlsen.webapitester.run;

import lombok.extern.log4j.Log4j2;
import me.nehlsen.webapitester.run.context.PlanExecutionContext;
import me.nehlsen.webapitester.run.context.TaskExecutionContext;
import me.nehlsen.webapitester.run.context.TaskExecutionContextFactory;
import me.nehlsen.webapitester.run.dto.TaskDto;
import me.nehlsen.webapitester.run.executor.TaskExecutor;

import java.util.List;

@Log4j2
public class TaskRunner {

    private final List<TaskExecutor> taskExecutors;
    private final PlanExecutionContext planExecutionContext;
    private final TaskExecutionContextFactory taskExecutionContextFactory;

    public TaskRunner(
            List<TaskExecutor> taskExecutors,
            PlanExecutionContext planExecutionContext,
            TaskExecutionContextFactory taskExecutionContextFactory
    ) {
        this.taskExecutors = taskExecutors;
        this.planExecutionContext = planExecutionContext;
        this.taskExecutionContextFactory = taskExecutionContextFactory;
    }

    public void execute(TaskDto task) {
        log.info("execute: \"{}\"", task.getName());

        final TaskExecutionContext taskExecutionContext = taskExecutionContextFactory.createContext(task, planExecutionContext);

        taskExecutors.stream()
                .filter(taskExecutor -> taskExecutor.supports(task))
                .forEach(taskExecutor -> taskExecutor.execute(taskExecutionContext));
    }
}
