package me.nehlsen.webapitester.api.task;

import lombok.Value;
import me.nehlsen.webapitester.api.assertion.AssertionDto;

import java.util.List;
import java.util.Map;

@Value
public class TaskDto {
    String uuid;
    String type;
    String name;
    String uri;
    Map<String, String> parameters;
    List<AssertionDto> assertions;
}
