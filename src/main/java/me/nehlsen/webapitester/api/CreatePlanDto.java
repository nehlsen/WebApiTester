package me.nehlsen.webapitester.api;

import lombok.Value;

import java.util.List;

@Value
public class CreatePlanDto {
    String name;
    List<CreateTaskDto> tasks;
}
