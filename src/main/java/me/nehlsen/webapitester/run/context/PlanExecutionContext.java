package me.nehlsen.webapitester.run.context;

import lombok.Getter;
import lombok.Setter;
import me.nehlsen.webapitester.run.dto.PlanDto;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Getter
public class PlanExecutionContext {

    private final UUID uuid = UUID.randomUUID();
    private final PlanDto plan;
    private final List<TaskExecutionContext> taskExecutionContexts = new LinkedList<>();

    @Setter
    private UUID recordUuid;

    public PlanExecutionContext(PlanDto plan) {
        this.plan = plan;
    }

    public void addTaskExecutionContext(TaskExecutionContext taskExecutionContext) {
        taskExecutionContexts.add(taskExecutionContext);
    }

    public boolean isResultPositive() {
        return taskExecutionContexts
                .stream()
                .map(TaskExecutionContext::isResultPositive)
                .filter(result -> !result)
                .findFirst()
                .orElse(true);
    }
}
