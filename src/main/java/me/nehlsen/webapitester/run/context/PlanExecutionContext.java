package me.nehlsen.webapitester.run.context;

import me.nehlsen.webapitester.persistence.plan.PlanEntity;

import java.util.LinkedList;
import java.util.List;

public class PlanExecutionContext {

    private final PlanEntity plan;
    private List<TaskExecutionContext> taskExecutionContexts;

    public PlanExecutionContext(PlanEntity plan) {
        this.plan = plan;
        this.taskExecutionContexts = new LinkedList<>();
    }

    public void addTaskExecutionContext(TaskExecutionContext taskExecutionContext) {
        taskExecutionContexts.add(taskExecutionContext);
    }
}
