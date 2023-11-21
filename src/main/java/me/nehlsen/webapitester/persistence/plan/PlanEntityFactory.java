package me.nehlsen.webapitester.persistence.plan;

import me.nehlsen.webapitester.api.plan.CreatePlanDto;
import me.nehlsen.webapitester.persistence.task.TaskEntityFactory;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class PlanEntityFactory {

    private final TaskEntityFactory taskEntityFactory;

    public PlanEntityFactory(TaskEntityFactory taskEntityFactory) {
        this.taskEntityFactory = taskEntityFactory;
    }

    public PlanEntity newPlan(CreatePlanDto dto) {
        Objects.requireNonNull(dto, "PlanEntityFactory::newPlan: requires non null CreatePlanDto");

        PlanEntity planEntity = new PlanEntity();

        planEntity.setName(dto.getName());
        planEntity.setTasks(dto.getTasks().stream().map(taskEntityFactory::newTask).toList());
        planEntity.setSchedule(dto.getSchedule());
        planEntity.setScheduleActive(dto.isScheduleActive());

        return planEntity;
    }

}
