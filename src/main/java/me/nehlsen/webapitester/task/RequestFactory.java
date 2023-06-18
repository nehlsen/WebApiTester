package me.nehlsen.webapitester.task;

import org.springframework.stereotype.Component;

import java.net.http.HttpRequest;

@Component
public class RequestFactory {
    public HttpRequest createRequest(TaskExecutionContext context) {
        return context.getTask().createRequest(createRequestBuilder());
    }

    private HttpRequest.Builder createRequestBuilder() {
        return HttpRequest.newBuilder();
    }
}
