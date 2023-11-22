package me.nehlsen.webapitester.run.event;

import lombok.Getter;
import me.nehlsen.webapitester.run.context.PlanExecutionContext;
import org.springframework.context.ApplicationEvent;

@Getter
abstract class RunPlanEvent extends ApplicationEvent {
    private final PlanExecutionContext planExecutionContext;

    public RunPlanEvent(Object source, PlanExecutionContext planExecutionContext) {
        super(source);
        this.planExecutionContext = planExecutionContext;
    }
}
