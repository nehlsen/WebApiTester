package me.nehlsen.webapitester.api;

import lombok.Value;

import java.util.List;

@Value
public class CreatePlanRequestData {
    String name;
    List<TaskDto> tasks;
}
