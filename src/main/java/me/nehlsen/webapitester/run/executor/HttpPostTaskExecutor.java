package me.nehlsen.webapitester.run.executor;

import me.nehlsen.webapitester.run.context.TaskExecutionContext;
import me.nehlsen.webapitester.run.dto.HttpPostTaskDto;
import me.nehlsen.webapitester.run.dto.HttpRequestResponseMapper;
import me.nehlsen.webapitester.run.dto.TaskDto;
import me.nehlsen.webapitester.run.executor.http.HttpClientFactory;
import me.nehlsen.webapitester.run.executor.http.HttpTaskExecutor;
import org.springframework.stereotype.Component;

import java.net.http.HttpRequest;

@Component
public class HttpPostTaskExecutor extends HttpTaskExecutor {

    private final RequestBodyFactory requestBodyFactory;

    public HttpPostTaskExecutor(
            HttpClientFactory httpClientFactory,
            HttpRequestResponseMapper requestResponseMapper,
            RequestBodyFactory requestBodyFactory
    ) {
        super(httpClientFactory, requestResponseMapper);
        this.requestBodyFactory = requestBodyFactory;
    }

    @Override
    public boolean supports(TaskDto task) {
        return task instanceof HttpPostTaskDto;
    }

    @Override
    protected String requestMethod() {
        return "POST";
    }

    @Override
    protected HttpRequest.BodyPublisher requestBody(TaskExecutionContext context) {
        return HttpRequest.BodyPublishers.ofString(evaluateRequestBody(context));
    }

    private String evaluateRequestBody(TaskExecutionContext context) {
        return requestBodyFactory.buildBody(context);
    }
}
