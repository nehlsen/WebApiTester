package me.nehlsen.webapitester.run.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class HttpResponseDto {
    private int statusCode;
    private Map<String, List<String>> headers;

    private long responseTimeMillis;
}
