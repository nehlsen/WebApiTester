package me.nehlsen.webapitester.run.scheduler;

import me.nehlsen.webapitester.run.PlanRunner;
import me.nehlsen.webapitester.run.dto.PlanDto;
import org.springframework.stereotype.Component;

@Component
public class SpringSchedulerTaskFactory {

    private final PlanRunner planRunner;

    public SpringSchedulerTaskFactory(PlanRunner planRunner) {
        this.planRunner = planRunner;
    }

    public SpringSchedulerTask create(PlanDto plan) {
        return new SpringSchedulerTask(planRunner, plan);
    }
}
