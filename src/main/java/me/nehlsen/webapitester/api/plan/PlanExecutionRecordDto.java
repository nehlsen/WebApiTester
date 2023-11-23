package me.nehlsen.webapitester.api.plan;

import lombok.Data;

import java.util.Date;

@Data
public class PlanExecutionRecordDto {
    String uuid;
    long runtimeMillis;
    Date timestamp;
}
