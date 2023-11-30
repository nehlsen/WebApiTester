package me.nehlsen.webapitester.run.event;

import me.nehlsen.webapitester.run.context.TaskExecutionContext;

public class BeforeRunTaskEvent extends RunTaskEvent {

    public BeforeRunTaskEvent(Object source, TaskExecutionContext taskExecutionContext) {
        super(source, taskExecutionContext);
    }
}
