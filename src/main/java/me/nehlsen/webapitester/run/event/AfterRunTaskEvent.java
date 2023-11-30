package me.nehlsen.webapitester.run.event;

import me.nehlsen.webapitester.run.context.TaskExecutionContext;

public class AfterRunTaskEvent extends RunTaskEvent {

    public AfterRunTaskEvent(Object source, TaskExecutionContext taskExecutionContext) {
        super(source, taskExecutionContext);
    }
}
