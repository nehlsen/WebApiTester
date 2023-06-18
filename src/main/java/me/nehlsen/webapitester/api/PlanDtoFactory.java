package me.nehlsen.webapitester.api;

import me.nehlsen.webapitester.plan.Plan;
import org.springframework.stereotype.Component;

@Component
public class PlanDtoFactory {

    private final TaskDtoFactory taskDtoFactory;

    public PlanDtoFactory(TaskDtoFactory taskDtoFactory) {
        this.taskDtoFactory = taskDtoFactory;
    }

    public PlanDto fromPlan(Plan plan) {
        return new PlanDto(
                plan.getUuid(),
                plan.getName(),
                plan.getTasks().stream().map(taskDtoFactory::fromTask).toList()
        );
    }
}
