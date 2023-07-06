package me.nehlsen.webapitester.run.context;

import me.nehlsen.webapitester.run.dto.PlanDto;

import java.util.LinkedList;
import java.util.List;

public class PlanExecutionContext {

    private final PlanDto plan;
    private List<TaskExecutionContext> taskExecutionContexts;

    public PlanExecutionContext(PlanDto plan) {
        this.plan = plan;
        this.taskExecutionContexts = new LinkedList<>();
    }

    public void addTaskExecutionContext(TaskExecutionContext taskExecutionContext) {
        taskExecutionContexts.add(taskExecutionContext);
    }
}
