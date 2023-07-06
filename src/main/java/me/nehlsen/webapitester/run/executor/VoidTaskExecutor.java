package me.nehlsen.webapitester.run.executor;

import me.nehlsen.webapitester.run.context.TaskExecutionContext;
import me.nehlsen.webapitester.run.dto.TaskDto;
import me.nehlsen.webapitester.run.dto.VoidTaskDto;
import org.springframework.stereotype.Component;

@Component
public class VoidTaskExecutor implements TaskExecutor {
    @Override
    public boolean supports(TaskDto task) {
        return task instanceof VoidTaskDto;
    }

    @Override
    public void execute(TaskExecutionContext context) {
        // NO-OP
    }
}
