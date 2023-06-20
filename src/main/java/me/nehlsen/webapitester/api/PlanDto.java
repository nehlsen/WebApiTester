package me.nehlsen.webapitester.api;

import lombok.Value;

import java.util.List;

@Value
public class PlanDto {
    String uuid;
    String name;
    List<TaskDto> tasks;
}
