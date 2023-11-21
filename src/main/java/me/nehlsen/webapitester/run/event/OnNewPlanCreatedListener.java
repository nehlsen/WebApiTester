package me.nehlsen.webapitester.run.event;

import me.nehlsen.webapitester.run.scheduler.RunScheduler;
import me.nehlsen.webapitester.persistence.event.AfterCreatePlanEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class OnNewPlanCreatedListener {
    private final RunScheduler runScheduler;

    public OnNewPlanCreatedListener(RunScheduler runScheduler) {
        this.runScheduler = runScheduler;
    }

    @EventListener
    public void registerScheduleForNewPlan(AfterCreatePlanEvent afterCreatePlanEvent) {
        runScheduler.schedule(afterCreatePlanEvent.getPlanEntity());
    }
}
