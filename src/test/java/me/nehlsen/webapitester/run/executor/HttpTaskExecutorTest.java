package me.nehlsen.webapitester.run.executor;

import me.nehlsen.webapitester.run.context.TaskExecutionContext;
import me.nehlsen.webapitester.run.dto.HttpRequestDto;
import me.nehlsen.webapitester.run.dto.HttpRequestResponseMapper;
import me.nehlsen.webapitester.run.dto.HttpResponseDto;
import me.nehlsen.webapitester.run.dto.HttpTaskDto;
import me.nehlsen.webapitester.run.executor.http.HttpClientFactory;
import me.nehlsen.webapitester.run.executor.http.HttpTaskExecutor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public abstract class HttpTaskExecutorTest {

    protected HttpClientFactory httpClientFactory;
    protected HttpRequestResponseMapper httpRequestResponseMapper;
    protected RequestBodyFactory requestBodyFactory;
    protected HttpTaskExecutor httpTaskExecutor;

    @BeforeEach
    void setUp() {
        httpClientFactory = createHttpClientFactory();
        httpRequestResponseMapper = createHttpRequestResponseMapper();
        requestBodyFactory = mock(RequestBodyFactory.class);
        when(requestBodyFactory.buildBody(any())).thenReturn("the body of the request");
    }

    protected HttpClientFactory createHttpClientFactory() {
        return mock(HttpClientFactory.class);
    }

    protected HttpRequestResponseMapper createHttpRequestResponseMapper() {
        return mock(HttpRequestResponseMapper.class);
    }

    @Test
    public void it_calls_send_and_updates_the_context() throws IOException, InterruptedException {
        final HttpRequestDto httpRequestDto = makeRequestResponseMapperMapRequests();
        final HttpResponseDto httpResponseDto = makeRequestResponseMapperMapResponses();
        final TaskExecutionContext taskExecutionContext = createTaskExecutionContext();

        final HttpClient httpClient = mock(HttpClient.class);
        final HttpResponse httpResponse = mock(HttpResponse.class);
        when(httpClient.send(any(HttpRequest.class), any())).thenReturn(httpResponse);
        when(httpClientFactory.createClient()).thenReturn(httpClient);

        // when
        httpTaskExecutor.execute(taskExecutionContext);

        // then
        verify(taskExecutionContext).setRequest(httpRequestDto);
        verify(httpClient).send(any(HttpRequest.class), any());
        verify(httpResponseDto).setResponseTimeMillis(anyLong());
        verify(taskExecutionContext).setResponse(httpResponseDto);
        verify(taskExecutionContext, never()).setRequestFailed(any());
    }

    private TaskExecutionContext createTaskExecutionContext() {
        final HttpTaskDto httpTaskDto = mock(HttpTaskDto.class);
        when(httpTaskDto.getUri()).thenReturn(URI.create("http://some.uri.com"));

        final TaskExecutionContext taskExecutionContext = mock(TaskExecutionContext.class);
        when(taskExecutionContext.getTask()).thenReturn(httpTaskDto);

        return taskExecutionContext;
    }

    private HttpRequestDto makeRequestResponseMapperMapRequests() {
        final HttpRequestDto httpRequestDto = mock(HttpRequestDto.class);
        when(httpRequestResponseMapper.toDto(any(HttpRequest.class))).thenReturn(httpRequestDto);

        return httpRequestDto;
    }

    private HttpResponseDto makeRequestResponseMapperMapResponses() {
        final HttpResponseDto httpResponseDto = mock(HttpResponseDto.class);
        when(httpRequestResponseMapper.toDto(any(HttpResponse.class))).thenReturn(httpResponseDto);

        return httpResponseDto;
    }
}
