package me.nehlsen.webapitester.run.event;

import me.nehlsen.webapitester.run.recorder.TaskExecutionRecorder;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class TaskExecutionListener {
    private final TaskExecutionRecorder taskExecutionRecorder;

    public TaskExecutionListener(TaskExecutionRecorder taskExecutionRecorder) {
        this.taskExecutionRecorder = taskExecutionRecorder;
    }

    @EventListener
    public void onExecutionStart(BeforeRunTaskEvent beforeRunTaskEvent) {
        taskExecutionRecorder.executionStart(beforeRunTaskEvent.getTaskExecutionContext());
    }

    @EventListener
    public void onExecutionEnd(AfterRunTaskEvent afterRunTaskEvent) {
        taskExecutionRecorder.executionEnd(afterRunTaskEvent.getTaskExecutionContext());
    }
}
