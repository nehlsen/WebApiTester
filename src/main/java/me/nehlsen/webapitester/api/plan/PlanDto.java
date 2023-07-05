package me.nehlsen.webapitester.api.plan;

import lombok.Value;
import me.nehlsen.webapitester.api.task.TaskDto;

import java.util.List;

@Value
public class PlanDto {
    String uuid;
    String name;
    List<TaskDto> tasks;
}
