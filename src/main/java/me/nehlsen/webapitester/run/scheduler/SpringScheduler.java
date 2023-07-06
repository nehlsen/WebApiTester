package me.nehlsen.webapitester.run.scheduler;

import me.nehlsen.webapitester.persistence.plan.PlanEntity;
import me.nehlsen.webapitester.run.dto.PlanDto;
import me.nehlsen.webapitester.run.dto.RunMapper;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class SpringScheduler implements RunScheduler {

    private final TaskScheduler taskScheduler;
    private final SpringSchedulerTaskFactory taskFactory;
    private final RunMapper runMapper;

    public SpringScheduler(TaskScheduler taskScheduler, SpringSchedulerTaskFactory taskFactory, RunMapper runMapper) {
        this.taskScheduler = taskScheduler;
        this.taskFactory = taskFactory;
        this.runMapper = runMapper;
    }

    @Override
    public void scheduleNow(PlanEntity plan) {
        final PlanDto planDto = runMapper.planEntityToDto(plan);

        taskScheduler.schedule(
                taskFactory.create(planDto),
                Instant.now()
        );
    }
}
