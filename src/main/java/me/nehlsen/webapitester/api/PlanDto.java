package me.nehlsen.webapitester.api;

import lombok.Value;

import java.util.List;
import java.util.UUID;

@Value
public class PlanDto {
    UUID uuid;
    String name;
    List<TaskDto> tasks;
}
