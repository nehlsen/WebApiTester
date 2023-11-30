package me.nehlsen.webapitester.run.context;

import me.nehlsen.webapitester.run.dto.TaskDto;
import org.springframework.stereotype.Component;

@Component
public class TaskExecutionContextFactory {
    public TaskExecutionContext createContext(TaskDto task, PlanExecutionContext planExecutionContext, TaskExecutionContext previousContext) {
        final TaskExecutionContext taskExecutionContext = new TaskExecutionContext(task, planExecutionContext, previousContext);
        planExecutionContext.addTaskExecutionContext(taskExecutionContext);

        return taskExecutionContext;
    }
}
