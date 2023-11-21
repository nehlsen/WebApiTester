package me.nehlsen.webapitester.api.plan;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Value;
import me.nehlsen.webapitester.api.task.CreateTaskDto;

import java.util.List;

@Value
@AllArgsConstructor(onConstructor = @__(@JsonCreator))
public class CreatePlanDto {
    String name;
    @NotNull
    List<CreateTaskDto> tasks;
    String schedule;
    boolean scheduleActive;

    public CreatePlanDto(String name, List<CreateTaskDto> tasks) {
        this(name, tasks, "", false);
    }
}
