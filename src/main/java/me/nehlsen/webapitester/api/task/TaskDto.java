package me.nehlsen.webapitester.api.task;

import lombok.Value;

@Value
public class TaskDto {
    String uuid;
    String type;
    String name;
    String uri;
}
