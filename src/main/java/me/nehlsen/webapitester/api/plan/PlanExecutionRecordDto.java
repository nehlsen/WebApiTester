package me.nehlsen.webapitester.api.plan;

import lombok.Data;

import java.util.Date;

@Data
public class PlanExecutionRecordDto {
    private String uuid;
    private long runtimeMillis;
    private boolean resultPositive;
    private Date timestamp;
}
