package me.nehlsen.webapitester.run.dto;

import me.nehlsen.webapitester.fixture.PlanEntityFixture;
import me.nehlsen.webapitester.fixture.TaskEntityFixture;
import me.nehlsen.webapitester.persistence.plan.PlanEntity;
import me.nehlsen.webapitester.persistence.task.HttpGetTaskEntity;
import me.nehlsen.webapitester.persistence.task.VoidTaskEntity;
import me.nehlsen.webapitester.persistence.assertion.RequestTimeAssertionEntity;
import me.nehlsen.webapitester.persistence.assertion.ResponseStatusCodeAssertionEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

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
        assertThat(voidTaskDto.getAssertions()).hasSize(voidTaskEntity.getAssertions().size());
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
        assertThat(httpGetTaskDto.getAssertions()).hasSize(httpGetTaskEntity.getAssertions().size());
    }

    @Test
    public void map_request_time_assertion_entity_to_dto() {
        final RequestTimeAssertionEntity requestTimeAssertionEntity = new RequestTimeAssertionEntity(444);
        requestTimeAssertionEntity.setUuid(UUID.fromString("af70b1d3-ad41-4d9f-b44a-f3cc7524d142"));

        final AssertionDto assertionDto = mapper.assertionEntityToDto(requestTimeAssertionEntity);
        assertThat(assertionDto).isInstanceOf(RequestTimeAssertionDto.class);

        final RequestTimeAssertionDto requestTimeAssertionDto = mapper.requestTimeAssertionEntityToDto(requestTimeAssertionEntity);
        assertThat(requestTimeAssertionDto.getUuid()).isEqualTo(requestTimeAssertionEntity.getUuid());
        assertThat(requestTimeAssertionDto.getMaximumRequestTimeMillis()).isEqualTo(requestTimeAssertionEntity.getMaximumRequestTimeMillis());
    }

    @Test
    public void map_response_status_code_assertion_entity_to_dto() {
        final ResponseStatusCodeAssertionEntity responseStatusCodeAssertionEntity = new ResponseStatusCodeAssertionEntity(666);
        responseStatusCodeAssertionEntity.setUuid(UUID.fromString("af70b1d3-ad41-4d9f-b44a-f3cc7524d142"));

        final AssertionDto assertionDto = mapper.assertionEntityToDto(responseStatusCodeAssertionEntity);
        assertThat(assertionDto).isInstanceOf(ResponseStatusCodeAssertionDto.class);

        final ResponseStatusCodeAssertionDto responseStatusCodeAssertionDto = mapper.responseStatusCodeAssertionEntityToDto(responseStatusCodeAssertionEntity);
        assertThat(responseStatusCodeAssertionDto.getUuid()).isEqualTo(responseStatusCodeAssertionEntity.getUuid());
        assertThat(responseStatusCodeAssertionDto.getExpectedStatusCode()).isEqualTo(responseStatusCodeAssertionEntity.getExpectedStatusCode());
    }
}
