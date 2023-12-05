package me.nehlsen.webapitester.api.task;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.With;
import me.nehlsen.webapitester.api.assertion.CreateAssertionDto;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class CreateTaskDto {
    String type;
    String name;
    @With
    String uri;
    @With
    List<CreateAssertionDto> assertions = List.of();
    @With
    Map<String, String> parameters = Map.of();
    @With
    Map<String, List<String>> headers = Map.of();

    public CreateTaskDto(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public CreateTaskDto(String type, String name, String uri) {
        this.type = type;
        this.name = name;
        this.uri = uri;
    }
}
