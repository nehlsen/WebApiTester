package me.nehlsen.webapitester.task;

import org.springframework.stereotype.Component;

@Component
public class TaskExecutionContextFactory {
    public TaskExecutionContext createContext(Task task) {
        return new TaskExecutionContext(task);
    }
}
