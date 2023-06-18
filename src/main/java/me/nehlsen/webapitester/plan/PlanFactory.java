package me.nehlsen.webapitester.plan;

import me.nehlsen.webapitester.api.CreatePlanRequestData;
import me.nehlsen.webapitester.task.TaskFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PlanFactory {
    private final TaskFactory taskFactory;

    public PlanFactory(TaskFactory taskFactory) {
        this.taskFactory = taskFactory;
    }

    public Plan createPlanFromDto(CreatePlanRequestData planDto) {
        return new Plan(
                UUID.randomUUID(),
                planDto.getName(),
                planDto.getTasks().stream().map(taskFactory::fromDto).toList()
        );
    }
}
