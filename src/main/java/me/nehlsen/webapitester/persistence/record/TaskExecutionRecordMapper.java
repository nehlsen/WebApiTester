package me.nehlsen.webapitester.persistence.record;

import me.nehlsen.webapitester.run.dto.HttpRequestDto;
import me.nehlsen.webapitester.run.dto.HttpResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TaskExecutionRecordMapper {
    Request mapRequest(HttpRequestDto request);
    Response mapResponse(HttpResponseDto response);
}
