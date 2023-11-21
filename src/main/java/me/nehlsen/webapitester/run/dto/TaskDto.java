package me.nehlsen.webapitester.run.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
abstract public class TaskDto {

    UUID uuid;
    String name;
    List<AssertionDto> assertions;
}