package me.nehlsen.webapitester.run.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class PlanDto {

    UUID uuid;
    String name;
    List<TaskDto> tasks;
}
