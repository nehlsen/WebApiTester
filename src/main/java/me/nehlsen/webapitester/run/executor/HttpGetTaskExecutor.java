package me.nehlsen.webapitester.run.executor;

import lombok.extern.log4j.Log4j2;
import me.nehlsen.webapitester.run.context.TaskExecutionContext;
import me.nehlsen.webapitester.run.dto.HttpGetTaskDto;
import me.nehlsen.webapitester.run.dto.HttpRequestResponseMapper;
import me.nehlsen.webapitester.run.dto.HttpResponseDto;
import me.nehlsen.webapitester.run.dto.TaskDto;
import me.nehlsen.webapitester.util.FunctionCallStopwatch;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Component @Log4j2
public class HttpGetTaskExecutor implements TaskExecutor {

    @Value("${webapitester.default_request_timeout_seconds:10}")
    private final int DEFAULT_REQUEST_TIMEOUT_SECONDS = 10;

    private final HttpRequestResponseMapper requestResponseMapper;

    public HttpGetTaskExecutor(HttpRequestResponseMapper requestResponseMapper) {
        this.requestResponseMapper = requestResponseMapper;
    }

    @Override
    public boolean supports(TaskDto task) {
        return task instanceof HttpGetTaskDto;
    }

    @Override
    public void execute(TaskExecutionContext context) {
        runRequest(
                context,
                createRequest((HttpGetTaskDto) context.getTask())
        );
    }

    private HttpRequest createRequest(HttpGetTaskDto httpGetTask) {
        return createRequestBuilder()
                .uri(httpGetTask.getUri())
                .timeout(Duration.of(DEFAULT_REQUEST_TIMEOUT_SECONDS, ChronoUnit.SECONDS))
                .GET()
                .build();
    }

    private HttpRequest.Builder createRequestBuilder() {
        return HttpRequest.newBuilder();
    }

    private void runRequest(TaskExecutionContext context, HttpRequest httpRequest) {
        log.info("runRequest: {} {}", context.getRequest().getMethod(), context.getRequest().getUri());
        context.setRequest(requestResponseMapper.toDto(httpRequest));

        FunctionCallStopwatch<Optional<HttpResponse<?>>> stopWatch = new FunctionCallStopwatch<>();
        stopWatch
                .run(() -> {
                    try {
                        return Optional.of(executeHttpRequest(httpRequest));
                    }  catch (HttpTimeoutException e) {
                        log.warn("runRequest: FAILED, http-timeout: {}", e.getMessage());
                        context.setRequestFailed(TaskExecutionContext.RequestFailedReason.HttpTimeout);
                    } catch (IOException|InterruptedException e) {
                        log.warn("runRequest: FAILED: {}", e.getMessage());
                        context.setRequestFailed(TaskExecutionContext.RequestFailedReason.OtherReason);
                    }
                    return Optional.empty();
                })
                .ifPresent((response) -> {
                    final HttpResponseDto responseDto = requestResponseMapper.toDto(response);
                    responseDto.setResponseTimeMillis(stopWatch.getTimeMillis());
                    context.setResponse(responseDto);
                });
    }

    private HttpResponse<String> executeHttpRequest(HttpRequest httpRequest) throws IOException, InterruptedException {
        return getHttpClient().send(httpRequest, HttpResponse.BodyHandlers.ofString());
    }

    private static HttpClient getHttpClient() {
        return HttpClient.newBuilder().build();
    }
}
