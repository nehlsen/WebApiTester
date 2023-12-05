package me.nehlsen.webapitester.api.record;

import me.nehlsen.webapitester.persistence.record.PlanExecutionRecordEntity;
import me.nehlsen.webapitester.persistence.record.Request;
import me.nehlsen.webapitester.persistence.record.Response;
import me.nehlsen.webapitester.persistence.record.TaskExecutionRecordEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ExecutionRecordMapper {

    @Mapping(target = "runtimeMillis", expression = "java(executionRecordEntity.getEndTimeEpochMillis() - executionRecordEntity.getStartTimeEpochMillis())")
    @Mapping(target = "timestamp", source = "updated")
    PlanExecutionRecordDto planExecutionRecordEntityToDto(PlanExecutionRecordEntity executionRecordEntity);

    List<PlanExecutionRecordDto> planExecutionRecordsEntityToDtos(List<PlanExecutionRecordEntity> executionRecordEntities);

    @Mapping(target = "runtimeMillis", expression = "java(executionRecordEntity.getEndTimeEpochMillis() - executionRecordEntity.getStartTimeEpochMillis())")
    TaskExecutionRecordDto taskExecutionRecordEntityToDto(TaskExecutionRecordEntity executionRecordEntity);

    List<TaskExecutionRecordDto> taskExecutionRecordsEntityToDtos(List<TaskExecutionRecordEntity> executionRecordEntities);

    TaskExecutionRecordDto.RequestDto requestRecordEntityToDto(Request requestEntity);
    TaskExecutionRecordDto.ResponseDto responseRecordEntityToDto(Response responseEntity);
}
