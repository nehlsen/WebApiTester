package me.nehlsen.webapitester.run.context;

import me.nehlsen.webapitester.persistence.task.TaskEntity;
import org.springframework.stereotype.Component;

@Component
public class TaskExecutionContextFactory {
    public TaskExecutionContext createContext(TaskEntity task, PlanExecutionContext planExecutionContext) {
        final TaskExecutionContext taskExecutionContext = new TaskExecutionContext(task, planExecutionContext);
        planExecutionContext.addTaskExecutionContext(taskExecutionContext);

        return taskExecutionContext;
    }
}
