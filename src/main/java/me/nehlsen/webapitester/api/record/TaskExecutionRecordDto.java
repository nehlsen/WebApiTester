package me.nehlsen.webapitester.api.record;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
public class TaskExecutionRecordDto {
    private String uuid;
    private long runtimeMillis;
    private boolean resultPositive;
    private RequestDto request;
    private ResponseDto response;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RequestDto {
        private String method;
        private String uri;
        private Map<String, List<String>> headers;
        private String body;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResponseDto {
        private int statusCode;
        private Map<String, List<String>> headers;
        private String body;
    }
}
