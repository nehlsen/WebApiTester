package me.nehlsen.webapitester.run.scheduler;

import me.nehlsen.webapitester.run.PlanRunner;
import me.nehlsen.webapitester.run.dto.PlanDto;

public class SpringSchedulerTask implements Runnable {

    private final PlanRunner planRunner;
    private final PlanDto plan;

    public SpringSchedulerTask(PlanRunner planRunner, PlanDto plan) {
        this.planRunner = planRunner;
        this.plan = plan;
    }

    @Override
    public void run() {
        planRunner.execute(plan);
    }
}
