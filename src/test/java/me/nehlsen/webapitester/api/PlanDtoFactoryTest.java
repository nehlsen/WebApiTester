package me.nehlsen.webapitester.api;

import me.nehlsen.webapitester.api.plan.PlanDto;
import me.nehlsen.webapitester.api.plan.PlanDtoFactory;
import me.nehlsen.webapitester.api.task.TaskDtoFactory;
import me.nehlsen.webapitester.persistence.plan.PlanEntity;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class PlanDtoFactoryTest {
    @Test
    public void create_dto_from_entity_with_uuid_and_name_but_no_tasks() {
        final TaskDtoFactory taskDtoFactory = Mockito.mock(TaskDtoFactory.class);
        Mockito.verifyNoInteractions(taskDtoFactory);
        final PlanDtoFactory planDtoFactory = new PlanDtoFactory(taskDtoFactory);

        PlanEntity planEntity = new PlanEntity();
        planEntity.setUuid(UUID.fromString("af70b1d3-ad41-4d9f-b44a-f3cc7524d142"));
        planEntity.setName("a test plan");
        planEntity.setTasks(List.of());

        final PlanDto planDto = planDtoFactory.fromEntity(planEntity);
        assertThat(planDto.getUuid()).isEqualTo(planEntity.getUuid().toString());
        assertThat(planDto.getName()).isEqualTo(planEntity.getName());
        assertThat(planDto.getTasks()).isEmpty();
    }
}
