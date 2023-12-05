package me.nehlsen.webapitester.api.record;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PlanExecutionRecordDto {
    private String uuid;
    private long runtimeMillis;
    private boolean resultPositive;
    private Date timestamp;

    private List<TaskExecutionRecordDto> taskExecutionRecords;
}
