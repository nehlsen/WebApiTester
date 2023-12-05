package me.nehlsen.webapitester.run.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class HttpRequestDto {
    private String method;
    private String uri;
    private Map<String, List<String>> headers;
    private String body; // FIXME limit to e.g. 10kb
}
