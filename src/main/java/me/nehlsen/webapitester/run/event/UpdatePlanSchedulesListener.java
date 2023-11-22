package me.nehlsen.webapitester.run.event;

import me.nehlsen.webapitester.persistence.DataAccess;
import me.nehlsen.webapitester.persistence.event.AfterCreatePlanEvent;
import me.nehlsen.webapitester.persistence.plan.PlanEntity;
import me.nehlsen.webapitester.run.scheduler.RunScheduler;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class UpdatePlanSchedulesListener {
    private final RunScheduler runScheduler;
    private final DataAccess dataAccess;

    public UpdatePlanSchedulesListener(RunScheduler runScheduler, DataAccess dataAccess) {
        this.runScheduler = runScheduler;
        this.dataAccess = dataAccess;
    }

    @EventListener
    public void registerScheduleForNewPlan(AfterCreatePlanEvent afterCreatePlanEvent) {
        updatePlanSchedule(afterCreatePlanEvent.getPlanEntity());
    }

    @EventListener(ContextRefreshedEvent.class)
    public void registerScheduleForAllPersistedPlansAfterApplicationBoot() {
        dataAccess.findAll().forEach(this::updatePlanSchedule);
    }

    private void updatePlanSchedule(PlanEntity planEntity) {
        runScheduler.schedule(planEntity);
    }
}
