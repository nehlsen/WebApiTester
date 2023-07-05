package me.nehlsen.webapitester.api;

import lombok.Value;

@Value
public class CreateTaskDto {
    String type;
    String name;
    String uri;
}
