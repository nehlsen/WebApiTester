package me.nehlsen.webapitester.run;

import lombok.extern.log4j.Log4j2;
import me.nehlsen.webapitester.run.assertion.AssertionsChecker;
import me.nehlsen.webapitester.run.context.PlanExecutionContext;
import me.nehlsen.webapitester.run.context.TaskExecutionContext;
import me.nehlsen.webapitester.run.context.TaskExecutionContextFactory;
import me.nehlsen.webapitester.run.dto.TaskDto;
import me.nehlsen.webapitester.run.event.AfterRunTaskEvent;
import me.nehlsen.webapitester.run.event.BeforeRunTaskEvent;
import me.nehlsen.webapitester.run.executor.TaskExecutor;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;

@Log4j2
public class TaskRunner {

    private final List<TaskExecutor> taskExecutors;
    private final PlanExecutionContext planExecutionContext;
    private final TaskExecutionContextFactory taskExecutionContextFactory;
    private final AssertionsChecker assertionsChecker;
    private final ApplicationEventPublisher applicationEventPublisher;

    public TaskRunner(
            List<TaskExecutor> taskExecutors,
            PlanExecutionContext planExecutionContext,
            TaskExecutionContextFactory taskExecutionContextFactory,
            AssertionsChecker assertionsChecker,
            ApplicationEventPublisher applicationEventPublisher) {
        this.taskExecutors = taskExecutors;
        this.planExecutionContext = planExecutionContext;
        this.taskExecutionContextFactory = taskExecutionContextFactory;
        this.assertionsChecker = assertionsChecker;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void execute(List<TaskDto> tasks) {
        TaskExecutionContext previousContext = null;
        for (TaskDto task : tasks) {
            previousContext = execute(task, previousContext);
        }
    }

    private TaskExecutionContext execute(TaskDto task, TaskExecutionContext previousContext) {
        log.info("Executing Task \"{}\"", task.getName());

        final TaskExecutionContext taskExecutionContext = taskExecutionContextFactory.createContext(
                task,
                planExecutionContext,
                previousContext
        );

        applicationEventPublisher.publishEvent(new BeforeRunTaskEvent(this, taskExecutionContext));
        taskExecutors.stream()
                .filter(taskExecutor -> taskExecutor.supports(task))
                .forEach(taskExecutor -> executeTaskAndRunAssertions(taskExecutor, taskExecutionContext));
        applicationEventPublisher.publishEvent(new AfterRunTaskEvent(this, taskExecutionContext));

        return taskExecutionContext;
    }

    private void executeTaskAndRunAssertions(TaskExecutor taskExecutor, TaskExecutionContext taskExecutionContext) {
        taskExecutor.execute(taskExecutionContext);
        assertionsChecker.check(taskExecutionContext);
    }
}
