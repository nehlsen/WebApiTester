package me.nehlsen.webapitester.api.task;

import lombok.Value;

@Value
public class CreateTaskDto {
    String type;
    String name;
    String uri;
}
