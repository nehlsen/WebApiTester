package me.nehlsen.webapitester.run.scheduler;

import lombok.extern.log4j.Log4j2;
import me.nehlsen.webapitester.persistence.plan.PlanEntity;
import me.nehlsen.webapitester.run.dto.PlanDto;
import me.nehlsen.webapitester.run.dto.RunMapper;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ScheduledFuture;

@Component
@Log4j2
public class SpringScheduler implements RunScheduler {

    private final TaskScheduler taskScheduler;
    private final SpringSchedulerTaskFactory taskFactory;
    private final RunMapper runMapper;
    private final Map<UUID, ScheduledFuture<?>> planSchedules = new HashMap<>();

    public SpringScheduler(TaskScheduler taskScheduler, SpringSchedulerTaskFactory taskFactory, RunMapper runMapper) {
        this.taskScheduler = taskScheduler;
        this.taskFactory = taskFactory;
        this.runMapper = runMapper;
    }

    @Override
    public void scheduleNow(PlanEntity plan) {
        log.info("Scheduling Plan \"{}\" to run <now>", plan.getName());

        final PlanDto planDto = runMapper.planEntityToDto(plan);

        // FIXME create a map UUID:ScheduledFuture -> remember what schedules we have, delete if necessary
        taskScheduler.schedule(
                taskFactory.create(planDto),
                Instant.now()
        );
    }

    @Override
    public void schedule(PlanEntity plan) {
        if (!plan.isScheduleActive() || plan.getSchedule().isEmpty()) {
            log.warn("NOT Scheduling Plan \"{}\", no Schedule or Schedule not active", plan.getName());
            return;
        }
        if (isPlanScheduleActive(plan)) {
            log.warn("NOT Scheduling Plan \"{}\", already Scheduled", plan.getName());
            return;
        }

        log.info("Scheduling Plan \"{}\" to run \"{}\"", plan.getName(), plan.getSchedule());

        CronTrigger trigger;
        try {
            trigger = new CronTrigger(plan.getSchedule());
        } catch (IllegalArgumentException illegalArgumentException) {
            // this might happen if the cron-expression can not be parsed or is invalid
            log.error("Scheduling Plan \"{}\" failed: \"{}\"", plan.getName(), illegalArgumentException.getMessage());

            return;
        }

        final PlanDto planDto = runMapper.planEntityToDto(plan);

        planSchedules.put(
                plan.getUuid(),
                taskScheduler.schedule(taskFactory.create(planDto), trigger)
        );
    }

    @Override
    public boolean isPlanScheduleActive(PlanEntity plan) {
        return planSchedules.containsKey(plan.getUuid());
    }
}
