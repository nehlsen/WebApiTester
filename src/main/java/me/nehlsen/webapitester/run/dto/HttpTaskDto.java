package me.nehlsen.webapitester.run.dto;

import lombok.Data;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Data
abstract public class HttpTaskDto extends TaskDto {

    URI uri;

    Map<String, List<String>> headers = Map.of();
}
