package me.nehlsen.webapitester.run.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.net.URI;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class HttpRequestResponseMapperTest {

    private HttpRequestResponseMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(HttpRequestResponseMapper.class);
    }

    @Test
    public void it_maps_http_request_to_dto() {
        final String url = "http://user:pass@host.name/path/deep?var=val";

        final Map<String, List<String>> headersMap = Map.of("head1", List.of("val1"));

        final HttpHeaders httpHeaders = mock(HttpHeaders.class);
        when(httpHeaders.map()).thenReturn(headersMap);

        final HttpRequest httpRequest = mock(HttpRequest.class);
        when(httpRequest.method()).thenReturn("HEAD");
        when(httpRequest.uri()).thenReturn(URI.create(url));
        when(httpRequest.headers()).thenReturn(httpHeaders);

        final HttpRequestDto dto = mapper.toDto(httpRequest);

        assertThat(dto.getMethod()).isEqualTo("HEAD");
        assertThat(dto.getUri()).isEqualTo(url);
        assertThat(dto.getHeaders()).isEqualTo(headersMap);
    }

    @Test
    public void it_maps_http_response_to_dto() {
        final Map<String, List<String>> headersMap = Map.of("head1", List.of("val1"));

        final HttpHeaders httpHeaders = mock(HttpHeaders.class);
        when(httpHeaders.map()).thenReturn(headersMap);

        final HttpResponse httpResponse = mock(HttpResponse.class);
        when(httpResponse.statusCode()).thenReturn(418);
        when(httpResponse.headers()).thenReturn(httpHeaders);
        when(httpResponse.body()).thenReturn("raw body data of response");

        final HttpResponseDto dto = mapper.toDto(httpResponse);

        assertThat(dto.getStatusCode()).isEqualTo(418);
        assertThat(dto.getHeaders()).isEqualTo(headersMap);
        assertThat(dto.getBody()).isEqualTo("raw body data of response");
    }
}
