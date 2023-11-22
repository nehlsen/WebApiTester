package me.nehlsen.webapitester.run.event;

import me.nehlsen.webapitester.run.recorder.PlanExecutionRecorder;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class PlanExecutionLister {
    private final PlanExecutionRecorder planExecutionRecorder;

    public PlanExecutionLister(PlanExecutionRecorder planExecutionRecorder) {
        this.planExecutionRecorder = planExecutionRecorder;
    }

    @EventListener
    public void onExecutionStart(BeforeRunPlanEvent beforeRunPlanEvent) {
        planExecutionRecorder.executionStart(beforeRunPlanEvent.getPlanExecutionContext());
    }

    @EventListener
    public void onExecutionEnd(AfterRunPlanEvent afterRunPlanEvent) {
        planExecutionRecorder.executionEnd(afterRunPlanEvent.getPlanExecutionContext());
    }
}
