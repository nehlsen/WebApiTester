package me.nehlsen.webapitester.run.executor;

import lombok.extern.log4j.Log4j2;
import me.nehlsen.webapitester.run.context.TaskExecutionContext;
import me.nehlsen.webapitester.run.dto.HttpGetTaskDto;
import me.nehlsen.webapitester.run.dto.TaskDto;
import me.nehlsen.webapitester.util.FunctionCallStopwatch;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Component @Log4j2
public class HttpGetTaskExecutor implements TaskExecutor {

    private final int DEFAULT_REQUEST_TIMEOUT_SECONDS = 10;

    @Override
    public boolean supports(TaskDto task) {
        return task instanceof HttpGetTaskDto;
    }

    @Override
    public void execute(TaskExecutionContext context) {
        context.setRequest(createRequest((HttpGetTaskDto) context.getTask()));
        context.setResponse(runRequest(context));
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

    private HttpResponse<String> runRequest(TaskExecutionContext context) {
        log.info("runRequest: {}", context.getRequest());

        FunctionCallStopwatch<HttpResponse<String>> stopWatch = new FunctionCallStopwatch<>();
        HttpResponse<String> response = stopWatch.run(() -> executeHttpRequest(context.getRequest()));
        context.setRequestTimeMillis(stopWatch.getTimeMillis());

        return response;
    }

    private HttpResponse<String> executeHttpRequest(HttpRequest httpRequest) {
        try {
            return getHttpClient().send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static HttpClient getHttpClient() {
        return HttpClient.newBuilder().build();
    }
}
