package me.nehlsen.webapitester.api;

import lombok.Value;

@Value
public class TaskDto {
    String type;
    String name;
    String uri;
}
