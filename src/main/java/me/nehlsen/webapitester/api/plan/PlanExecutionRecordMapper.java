package me.nehlsen.webapitester.api.plan;

import me.nehlsen.webapitester.persistence.record.PlanExecutionRecordEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PlanExecutionRecordMapper {

    @Mapping(target = "runtimeMillis", expression = "java(executionRecordEntity.getEndTimeEpochMillis() - executionRecordEntity.getStartTimeEpochMillis())")
    @Mapping(target = "timestamp", source = "updated")
    PlanExecutionRecordDto planExecutionRecordEntityToDto(PlanExecutionRecordEntity executionRecordEntity);

    List<PlanExecutionRecordDto> planExecutionRecordsEntityToDtos(List<PlanExecutionRecordEntity> executionRecordEntities);
}
