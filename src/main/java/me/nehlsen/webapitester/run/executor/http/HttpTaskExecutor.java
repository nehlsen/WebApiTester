package me.nehlsen.webapitester.run.executor.http;

import lombok.extern.log4j.Log4j2;
import me.nehlsen.webapitester.run.context.TaskExecutionContext;
import me.nehlsen.webapitester.run.dto.HttpRequestResponseMapper;
import me.nehlsen.webapitester.run.dto.HttpResponseDto;
import me.nehlsen.webapitester.run.dto.HttpTaskDto;
import me.nehlsen.webapitester.run.executor.TaskExecutor;
import me.nehlsen.webapitester.util.FunctionCallStopwatch;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Log4j2
public abstract class HttpTaskExecutor implements TaskExecutor {

    @Value("${webapitester.default_request_timeout_seconds:10}")
    private final int DEFAULT_REQUEST_TIMEOUT_SECONDS = 10;

    private final HttpClientFactory httpClientFactory;
    private final HttpRequestResponseMapper requestResponseMapper;

    public HttpTaskExecutor(
            HttpClientFactory httpClientFactory,
            HttpRequestResponseMapper requestResponseMapper
    ) {
        this.httpClientFactory = httpClientFactory;
        this.requestResponseMapper = requestResponseMapper;
    }

    @Override
    public void execute(TaskExecutionContext context) {
        runRequest(
                context,
                createRequest(context)
        );
    }

    private HttpRequest createRequest(TaskExecutionContext context) {
        final HttpTaskDto task = (HttpTaskDto) context.getTask();

        final HttpRequest.Builder requestBuilder = createRequestBuilder()
                .uri(task.getUri())
                .timeout(Duration.of(DEFAULT_REQUEST_TIMEOUT_SECONDS, ChronoUnit.SECONDS))
                .method(requestMethod(), requestBody(context));

        task.getHeaders().forEach((name, values) -> {
            values.forEach(value -> {
                requestBuilder.header(name, value);
            });
        });

        return requestBuilder.build();
    }

    abstract protected String requestMethod();

    abstract protected HttpRequest.BodyPublisher requestBody(TaskExecutionContext context);

    private HttpRequest.Builder createRequestBuilder() {
        return HttpRequest.newBuilder();
    }

    private void runRequest(TaskExecutionContext context, HttpRequest httpRequest) {
        log.info("runRequest: {} {}", httpRequest.method(), httpRequest.uri());
        context.setRequest(requestResponseMapper.toDto(httpRequest));

        FunctionCallStopwatch<Optional<HttpResponse<String>>> stopWatch = new FunctionCallStopwatch<>();
        stopWatch
                .run(() -> {
                    try {
                        return Optional.of(executeHttpRequest(httpRequest));
                    }  catch (HttpTimeoutException e) {
                        log.warn("runRequest: FAILED, http-timeout: {}", e.getMessage());
                        context.setRequestFailed(TaskExecutionContext.RequestFailedReason.HttpTimeout);
                    } catch (IOException | InterruptedException e) {
                        log.warn("runRequest: FAILED: {}", e.getMessage());
                        context.setRequestFailed(TaskExecutionContext.RequestFailedReason.OtherReason);
                    }
                    return Optional.empty();
                })
                .ifPresent((response) -> {
                    final HttpResponseDto responseDto = requestResponseMapper.toDto(response);
                    responseDto.setResponseTimeMillis(stopWatch.getTimeMillis());
                    context.setResponse(responseDto);

                    log.info("runRequest: SUCCESS, {}", response.statusCode());
                });
    }

    private HttpResponse<String> executeHttpRequest(HttpRequest httpRequest) throws IOException, InterruptedException {
        return httpClientFactory
                .createClient()
                .send(httpRequest, HttpResponse.BodyHandlers.ofString());
    }

}
