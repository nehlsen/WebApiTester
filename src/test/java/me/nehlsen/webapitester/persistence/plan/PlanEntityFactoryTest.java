package me.nehlsen.webapitester.persistence.plan;

import me.nehlsen.webapitester.api.plan.CreatePlanDto;
import me.nehlsen.webapitester.persistence.task.TaskEntityFactory;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PlanEntityFactoryTest {
    @Test
    public void new_plan_with_name_but_no_tasks() {
        final TaskEntityFactory taskEntityFactory = Mockito.mock(TaskEntityFactory.class);
        Mockito.verifyNoInteractions(taskEntityFactory);
        final PlanEntityFactory planEntityFactory = new PlanEntityFactory(taskEntityFactory);

        final CreatePlanDto createPlanDto = new CreatePlanDto("some name", List.of());
        final PlanEntity planEntity = planEntityFactory.newPlan(createPlanDto);

        assertThat(planEntity.getName()).isEqualTo(createPlanDto.getName());
    }
}