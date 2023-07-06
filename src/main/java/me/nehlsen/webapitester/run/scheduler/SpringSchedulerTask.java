package me.nehlsen.webapitester.run.scheduler;

import lombok.extern.log4j.Log4j2;
import me.nehlsen.webapitester.persistence.plan.PlanEntity;
import me.nehlsen.webapitester.run.PlanRunner;

@Log4j2
public class SpringSchedulerTask implements Runnable {

    private final PlanRunner planRunner;
    private final PlanEntity plan;

    public SpringSchedulerTask(PlanRunner planRunner, PlanEntity plan) {
        this.planRunner = planRunner;
        this.plan = plan;
    }

    @Override
    public void run() {
        log.info("SpringSchedulerTask.run: \"%s\"".formatted(plan.getName()));

        planRunner.execute(plan);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
        }

        log.info("SpringSchedulerTask.run --- some time later :)");
    }
}
