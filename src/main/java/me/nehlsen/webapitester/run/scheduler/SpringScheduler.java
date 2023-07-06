package me.nehlsen.webapitester.run.scheduler;

import me.nehlsen.webapitester.persistence.plan.PlanEntity;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class SpringScheduler implements RunScheduler {

    private final TaskScheduler taskScheduler;
    private final SpringSchedulerTaskFactory taskFactory;

    public SpringScheduler(TaskScheduler taskScheduler, SpringSchedulerTaskFactory taskFactory) {
        this.taskScheduler = taskScheduler;
        this.taskFactory = taskFactory;
    }

    @Override
    public void scheduleNow(PlanEntity plan) {
        taskScheduler.schedule(
                taskFactory.create(plan),
                Instant.now()
        );
    }
}
