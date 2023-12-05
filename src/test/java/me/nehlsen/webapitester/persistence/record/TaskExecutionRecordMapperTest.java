package me.nehlsen.webapitester.persistence.record;

import me.nehlsen.webapitester.run.dto.HttpRequestDto;
import me.nehlsen.webapitester.run.dto.HttpResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class TaskExecutionRecordMapperTest {

    private TaskExecutionRecordMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(TaskExecutionRecordMapper.class);
    }

    @Test
    public void it_maps_request() {
        HttpRequestDto request = new HttpRequestDto();
        request.setMethod("HEAD");
        request.setUri("https://mock.com/mockington");
        request.setHeaders(Map.of("Mock", List.of("it", "is")));
        request.setBody("request body data");

        final Request mappedRequest = mapper.mapRequest(request);

        assertThat(mappedRequest.getMethod()).isEqualTo(request.getMethod());
        assertThat(mappedRequest.getUri()).isEqualTo(request.getUri());
        assertThat(mappedRequest.getHeaders()).isEqualTo(request.getHeaders());
        assertThat(mappedRequest.getBody()).isEqualTo(request.getBody());
    }

    @Test
    public void it_maps_response() {
        HttpResponseDto response = new HttpResponseDto();
        response.setStatusCode(1337);
        response.setHeaders(Map.of("Mock", List.of("it", "is")));
        response.setBody("fancy data returned by the request");
        response.setResponseTimeMillis(65432);

        final Response mappedResponse = mapper.mapResponse(response);

        assertThat(mappedResponse.getStatusCode()).isEqualTo(response.getStatusCode());
        assertThat(mappedResponse.getHeaders()).isEqualTo(response.getHeaders());
        assertThat(mappedResponse.getBody()).isEqualTo(response.getBody());
        assertThat(mappedResponse.getResponseTimeMillis()).isEqualTo(response.getResponseTimeMillis());
    }
}
