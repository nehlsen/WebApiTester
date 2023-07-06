package me.nehlsen.webapitester.run.scheduler;

import me.nehlsen.webapitester.persistence.plan.PlanEntity;
import me.nehlsen.webapitester.run.PlanRunner;
import org.springframework.stereotype.Component;

@Component
public class SpringSchedulerTaskFactory {

    private final PlanRunner planRunner;

    public SpringSchedulerTaskFactory(PlanRunner planRunner) {
        this.planRunner = planRunner;
    }

    public SpringSchedulerTask create(PlanEntity plan) {
        return new SpringSchedulerTask(planRunner, plan);
    }
}
