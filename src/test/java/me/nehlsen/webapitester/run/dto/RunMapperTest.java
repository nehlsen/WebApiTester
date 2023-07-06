package me.nehlsen.webapitester.run.dto;

import me.nehlsen.webapitester.fixture.PlanEntityFixture;
import me.nehlsen.webapitester.fixture.TaskEntityFixture;
import me.nehlsen.webapitester.persistence.plan.PlanEntity;
import me.nehlsen.webapitester.persistence.task.HttpGetTaskEntity;
import me.nehlsen.webapitester.persistence.task.VoidTaskEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.*;

class RunMapperTest {

    private RunMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(RunMapper.class);
    }

    @Test
    public void map_plan_entity_without_tasks_to_dto() {
        PlanEntity planEntity = PlanEntityFixture.planWithoutTasks();

        final PlanDto planDto = mapper.planEntityToDto(planEntity);

        assertThat(planDto.getUuid()).isEqualTo(planEntity.getUuid());
        assertThat(planDto.getName()).isEqualTo(planEntity.getName());
        assertThat(planDto.getTasks()).hasSize(planEntity.getTasks().size());
    }

    @Test
    public void map_plan_entity_with_tasks_to_dto() {
        PlanEntity planEntity = PlanEntityFixture.planWithTasks();

        final PlanDto planDto = mapper.planEntityToDto(planEntity);

        assertThat(planDto.getUuid()).isEqualTo(planEntity.getUuid());
        assertThat(planDto.getName()).isEqualTo(planEntity.getName());
        assertThat(planDto.getTasks()).hasSize(planEntity.getTasks().size());
    }

    @Test
    public void map_void_task_entity_to_dto() {
        VoidTaskEntity voidTaskEntity = TaskEntityFixture.voidTaskEntityWithoutAssertions();

        final TaskDto taskDto = mapper.taskEntityToDto(voidTaskEntity);
        assertThat(taskDto).isInstanceOf(VoidTaskDto.class);

        final VoidTaskDto voidTaskDto = mapper.voidTaskEntityToDto(voidTaskEntity);
        assertThat(voidTaskDto.getUuid()).isEqualTo(voidTaskEntity.getUuid());
        assertThat(voidTaskDto.getName()).isEqualTo(voidTaskEntity.getName());

        // TODO check assertions
    }

    @Test
    public void map_http_get_task_entity_to_dto() {
        final HttpGetTaskEntity httpGetTaskEntity = TaskEntityFixture.httpGetTaskEntityWithoutAssertions();

        final TaskDto taskDto = mapper.taskEntityToDto(httpGetTaskEntity);
        assertThat(taskDto).isInstanceOf(HttpGetTaskDto.class);

        final HttpGetTaskDto httpGetTaskDto = mapper.httpGetTaskEntityToDto(httpGetTaskEntity);
        assertThat(httpGetTaskDto.getUuid()).isEqualTo(httpGetTaskEntity.getUuid());
        assertThat(httpGetTaskDto.getName()).isEqualTo(httpGetTaskEntity.getName());
        assertThat(httpGetTaskDto.getUri()).isEqualTo(httpGetTaskEntity.getUri());

        // TODO check assertions
    }
}
