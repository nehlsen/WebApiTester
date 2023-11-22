package me.nehlsen.webapitester.run.event;

import me.nehlsen.webapitester.run.context.PlanExecutionContext;

public class BeforeRunPlanEvent extends RunPlanEvent {

    public BeforeRunPlanEvent(Object source, PlanExecutionContext planExecutionContext) {
        super(source, planExecutionContext);
    }
}
