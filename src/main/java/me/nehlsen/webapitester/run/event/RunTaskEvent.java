package me.nehlsen.webapitester.run.event;

import lombok.Getter;
import me.nehlsen.webapitester.run.context.TaskExecutionContext;
import org.springframework.context.ApplicationEvent;

@Getter
abstract class RunTaskEvent extends ApplicationEvent {
    private final TaskExecutionContext taskExecutionContext;

    public RunTaskEvent(Object source, TaskExecutionContext taskExecutionContext) {
        super(source);
        this.taskExecutionContext = taskExecutionContext;
    }
}
