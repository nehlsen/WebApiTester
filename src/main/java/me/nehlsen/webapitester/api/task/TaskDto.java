package me.nehlsen.webapitester.api.task;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.With;
import me.nehlsen.webapitester.api.assertion.AssertionDto;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor(onConstructor = @__(@JsonCreator))
@RequiredArgsConstructor
public class TaskDto {
    private final String uuid;
    private final String type;
    private final String name;
    @With
    String uri = "";
    private final Map<String, String> parameters;
    private final List<AssertionDto> assertions;
    @With
    Map<String, List<String>> headers = Map.of();
}
