package me.nehlsen.webapitester.task;

import lombok.extern.log4j.Log4j2;
import me.nehlsen.webapitester.util.FunctionCallStopwatch;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

@Component @Log4j2
public class HttpRequestRunner {
    public HttpResponse<String> runRequest(TaskExecutionContext context) {
        log.info("runRequest: {}", context.getRequest());

        FunctionCallStopwatch<HttpResponse<String>> stopWatch = new FunctionCallStopwatch<>();
        HttpResponse<String> response = stopWatch.run(() -> executeHttpRequest(context));
        context.setRequestTimeMillis(stopWatch.getTimeMillis());
        return response;
    }

    private HttpResponse<String> executeHttpRequest(TaskExecutionContext context) {
        try {
            return getHttpClient().send(context.getRequest(), BodyHandlers.ofString());
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
