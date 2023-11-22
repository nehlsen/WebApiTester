package me.nehlsen.webapitester.api.plan;

import lombok.Data;

@Data
public class PlanExecutionRecordDto {
    String uuid;
    long runtimeMillis;
}
