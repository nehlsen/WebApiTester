package me.nehlsen.webapitester.run.event;

import me.nehlsen.webapitester.run.context.PlanExecutionContext;

public class AfterRunPlanEvent extends RunPlanEvent {

    public AfterRunPlanEvent(Object source, PlanExecutionContext planExecutionContext) {
        super(source, planExecutionContext);
    }
}
