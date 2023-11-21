package me.nehlsen.webapitester.run.scheduler;

import me.nehlsen.webapitester.persistence.plan.PlanEntity;

public interface RunScheduler {
    void scheduleNow(PlanEntity plan);
    void schedule(PlanEntity plan);
}
