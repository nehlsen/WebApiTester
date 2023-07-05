package me.nehlsen.webapitester.api.plan;

import lombok.AllArgsConstructor;
import lombok.Value;
import me.nehlsen.webapitester.api.task.CreateTaskDto;

import java.util.List;

@Value
@AllArgsConstructor
public class CreatePlanDto {
    String name;
    List<CreateTaskDto> tasks;

    public CreatePlanDto(String name) {
        this(name, List.of());
    }
}
