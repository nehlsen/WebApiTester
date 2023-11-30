package me.nehlsen.webapitester.run.recorder;

import lombok.extern.slf4j.Slf4j;
import me.nehlsen.webapitester.run.context.TaskExecutionContext;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TaskExecutionRecorder {
    public void executionStart(TaskExecutionContext taskExecutionContext) {
    }

    public void executionEnd(TaskExecutionContext taskExecutionContext) {
    }
}
