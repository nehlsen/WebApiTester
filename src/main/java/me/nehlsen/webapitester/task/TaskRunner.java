package me.nehlsen.webapitester.task;

import lombok.extern.log4j.Log4j2;
import me.nehlsen.webapitester.task.assertion.TaskAssertionsChecker;
import org.springframework.stereotype.Component;

@Component @Log4j2
public class TaskRunner {
    private final TaskExecutionContextFactory taskExecutionContextFactory;
    private final RequestFactory requestFactory;
    private final HttpRequestRunner requestRunner;
    private final TaskAssertionsChecker taskAssertionsChecker;

    public TaskRunner(
            TaskExecutionContextFactory taskExecutionContextFactory,
            RequestFactory requestFactory,
            HttpRequestRunner requestRunner,
            TaskAssertionsChecker taskAssertionsChecker
    ) {
        this.taskExecutionContextFactory = taskExecutionContextFactory;
        this.requestFactory = requestFactory;
        this.requestRunner = requestRunner;
        this.taskAssertionsChecker = taskAssertionsChecker;
    }

    public void runTask(Task task) {
        log.info("runTask");

        TaskExecutionContext context = taskExecutionContextFactory.createContext(task);

        context.setRequest(requestFactory.createRequest(context));
        context.setResponse(requestRunner.runRequest(context));
        taskAssertionsChecker.check(context);
    }
}
