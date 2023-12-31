package me.nehlsen.webapitester.api.plan;

import me.nehlsen.webapitester.api.task.TaskDtoFactory;
import me.nehlsen.webapitester.persistence.plan.PlanEntity;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class PlanDtoFactory {

    private final TaskDtoFactory taskDtoFactory;

    public PlanDtoFactory(TaskDtoFactory taskDtoFactory) {
        this.taskDtoFactory = taskDtoFactory;
    }

    public PlanDto fromEntity(PlanEntity plan) {
        Objects.requireNonNull(plan, "PlanDtoFactory::fromPlan: requires non null plan");

        return new PlanDto(
                plan.getUuid().toString(),
                plan.getName(),
                plan.getTasks().stream().map(taskDtoFactory::fromEntity).toList(),
                plan.getSchedule(),
                plan.isScheduleActive()
        );
    }
}
