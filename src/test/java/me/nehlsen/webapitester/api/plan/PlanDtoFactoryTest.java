package me.nehlsen.webapitester.api.plan;

import me.nehlsen.webapitester.api.task.TaskDtoFactory;
import me.nehlsen.webapitester.fixture.PlanEntityFixture;
import me.nehlsen.webapitester.persistence.plan.PlanEntity;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

class PlanDtoFactoryTest {
    @Test
    public void create_dto_from_entity_with_uuid_and_name_but_no_tasks() {
        final TaskDtoFactory taskDtoFactory = Mockito.mock(TaskDtoFactory.class);
        Mockito.verifyNoInteractions(taskDtoFactory);
        final PlanDtoFactory planDtoFactory = new PlanDtoFactory(taskDtoFactory);

        PlanEntity planEntity = PlanEntityFixture.planWithoutTasks();

        final PlanDto planDto = planDtoFactory.fromEntity(planEntity);
        assertThat(planDto.getUuid()).isEqualTo(planEntity.getUuid().toString());
        assertThat(planDto.getName()).isEqualTo(planEntity.getName());
        assertThat(planDto.getTasks()).isEmpty();
    }
}
