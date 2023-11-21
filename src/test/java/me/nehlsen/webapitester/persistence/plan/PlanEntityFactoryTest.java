package me.nehlsen.webapitester.persistence.plan;

import me.nehlsen.webapitester.api.plan.CreatePlanDto;
import me.nehlsen.webapitester.persistence.task.TaskEntityFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PlanEntityFactoryTest {

    private PlanEntityFactory planEntityFactory;

    @BeforeEach
    void setUp() {
        final TaskEntityFactory taskEntityFactory = Mockito.mock(TaskEntityFactory.class);
        Mockito.verifyNoInteractions(taskEntityFactory);
        planEntityFactory = new PlanEntityFactory(taskEntityFactory);
    }

    @Test
    public void new_plan_with_name_but_no_tasks() {
        final CreatePlanDto createPlanDto = new CreatePlanDto("some name", List.of());
        final PlanEntity planEntity = planEntityFactory.newPlan(createPlanDto);

        assertThat(planEntity.getName()).isEqualTo(createPlanDto.getName());
        assertThat(planEntity.getSchedule()).isEqualTo(createPlanDto.getSchedule());
        assertThat(planEntity.isScheduleActive()).isEqualTo(createPlanDto.isScheduleActive());
    }
    @Test
    public void new_plan_with_schedule() {
        final CreatePlanDto createPlanDto = new CreatePlanDto("some name", List.of(), "@daily", true);
        final PlanEntity planEntity = planEntityFactory.newPlan(createPlanDto);

        assertThat(planEntity.getName()).isEqualTo(createPlanDto.getName());
        assertThat(planEntity.getSchedule()).isEqualTo(createPlanDto.getSchedule());
        assertThat(planEntity.isScheduleActive()).isEqualTo(createPlanDto.isScheduleActive());
    }
}