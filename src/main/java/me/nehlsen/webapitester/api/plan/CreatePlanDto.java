package me.nehlsen.webapitester.api.plan;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Value;
import me.nehlsen.webapitester.api.task.CreateTaskDto;

import java.util.List;

@Value
@AllArgsConstructor
public class CreatePlanDto {
    String name;
    @NotNull
    List<CreateTaskDto> tasks;
}
