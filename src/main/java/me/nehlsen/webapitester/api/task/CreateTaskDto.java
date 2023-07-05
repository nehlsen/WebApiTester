package me.nehlsen.webapitester.api.task;

import lombok.AllArgsConstructor;
import lombok.Value;
import me.nehlsen.webapitester.api.assertion.CreateAssertionDto;

import java.util.List;

@Value
@AllArgsConstructor
public class CreateTaskDto {
    String type;
    String name;
    String uri;
    List<CreateAssertionDto> assertions;
}
