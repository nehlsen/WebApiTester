package me.nehlsen.webapitester.api.task;

import lombok.Value;
import me.nehlsen.webapitester.api.assertion.AssertionDto;

import java.util.List;

@Value
public class TaskDto {
    String uuid;
    String type;
    String name;
    String uri;
    List<AssertionDto> assertions;
}
