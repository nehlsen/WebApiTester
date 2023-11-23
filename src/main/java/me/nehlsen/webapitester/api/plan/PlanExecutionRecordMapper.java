package me.nehlsen.webapitester.api.plan;

import me.nehlsen.webapitester.persistence.plan.PlanExecutionRecordEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PlanExecutionRecordMapper {

    @Mapping(target = "runtimeMillis", expression = "java(executionRecordEntity.getEndTimeEpochMillis() - executionRecordEntity.getStartTimeEpochMillis())")
    @Mapping(target = "timestamp", source = "updated")
    PlanExecutionRecordDto planExecutionRecordEntityToDto(PlanExecutionRecordEntity executionRecordEntity);
}
