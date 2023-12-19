package me.nehlsen.webapitester.api.plan;

import me.nehlsen.webapitester.api.record.ExecutionRecordMapper;
import me.nehlsen.webapitester.api.record.PlanExecutionRecordDto;
import me.nehlsen.webapitester.api.record.TaskExecutionRecordDto;
import me.nehlsen.webapitester.persistence.record.PlanExecutionRecordEntity;
import me.nehlsen.webapitester.persistence.record.Request;
import me.nehlsen.webapitester.persistence.record.Response;
import me.nehlsen.webapitester.persistence.record.TaskExecutionRecordEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class ExecutionRecordMapperTest {

    private ExecutionRecordMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(ExecutionRecordMapper.class);
    }

    @Test
    public void it_maps_plan_execution_record_entity_to_dto() {
        final PlanExecutionRecordEntity entity = new PlanExecutionRecordEntity();
        entity.setUuid(UUID.fromString("673b2ec6-4325-456a-bf52-2693e3c4428a"));
        entity.setStartTimeEpochMillis(123000);
        entity.setEndTimeEpochMillis(123123);
        entity.setResultPositive(true);
        entity.setUpdated(new Date());

        final PlanExecutionRecordDto dto = mapper.planExecutionRecordEntityToDto(entity);

        assertThat(dto.getUuid()).isEqualTo(entity.getUuid().toString());
        assertThat(dto.getRuntimeMillis()).isEqualTo(123);
        assertThat(dto.isResultPositive()).isEqualTo(entity.isResultPositive());
        assertThat(dto.getTimestamp()).isEqualTo(entity.getUpdated());
    }

    @Test
    public void it_maps_task_execution_record_entity_to_dto() {
        final TaskExecutionRecordEntity entity = new TaskExecutionRecordEntity();
        entity.setUuid(UUID.fromString("123e4567-e89b-12d3-a456-556642440000"));
        entity.setStartTimeEpochMillis(123000);
        entity.setEndTimeEpochMillis(123123);
        entity.setResultPositive(true);
        entity.setRequest(new Request());
        entity.setResponse(new Response());

        final TaskExecutionRecordDto dto = mapper.taskExecutionRecordEntityToDto(entity);

        assertThat(dto.getUuid()).isEqualTo(entity.getUuid().toString());
        assertThat(dto.getRuntimeMillis()).isEqualTo(123);
        assertThat(dto.isResultPositive()).isEqualTo(entity.isResultPositive());
        assertThat(dto.getRequest()).isNotNull();
        assertThat(dto.getResponse()).isNotNull();
    }

    @Test
    public void it_maps_request_entity_to_dto() {
        final Request entity = new Request();
        entity.setMethod("MOCK");
        entity.setUri("https://some.sample.com/path?variable=value");
        entity.setHeaders(Map.of("Some-Header", List.of("some value")));
        entity.setBody("some request data");

        final TaskExecutionRecordDto.RequestDto dto = mapper.requestRecordEntityToDto(entity);

        assertThat(dto.getMethod()).isEqualTo(entity.getMethod());
        assertThat(dto.getUri()).isEqualTo(entity.getUri());
        assertThat(dto.getHeaders()).isEqualTo(entity.getHeaders());
        assertThat(dto.getBody()).isEqualTo(entity.getBody());
    }

    @Test
    public void it_maps_response_entity_to_dto() {
        final Response entity = new Response();
        entity.setStatusCode(222);
        entity.setHeaders(Map.of("Some-Header", List.of("some value")));
        entity.setBody("response body data");

        final TaskExecutionRecordDto.ResponseDto dto = mapper.responseRecordEntityToDto(entity);

        assertThat(dto.getStatusCode()).isEqualTo(entity.getStatusCode());
        assertThat(dto.getHeaders()).isEqualTo(entity.getHeaders());
        assertThat(dto.getBody()).isEqualTo(entity.getBody());
    }
}
