package me.nehlsen.webapitester.plan;

import lombok.Value;
import me.nehlsen.webapitester.task.Task;

import java.util.List;
import java.util.UUID;

@Value
public class Plan {
    UUID uuid;
    String name;
    List<Task> tasks;
}
