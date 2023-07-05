package me.nehlsen.webapitester.api.plan;

import lombok.Value;
import me.nehlsen.webapitester.api.task.CreateTaskDto;

import java.util.List;

@Value
public class CreatePlanDto {
    String name;
    List<CreateTaskDto> tasks;
}
