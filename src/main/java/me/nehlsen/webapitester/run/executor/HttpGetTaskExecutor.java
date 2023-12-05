package me.nehlsen.webapitester.run.executor;

import me.nehlsen.webapitester.run.context.TaskExecutionContext;
import me.nehlsen.webapitester.run.dto.HttpGetTaskDto;
import me.nehlsen.webapitester.run.dto.HttpRequestResponseMapper;
import me.nehlsen.webapitester.run.dto.TaskDto;
import me.nehlsen.webapitester.run.executor.http.HttpClientFactory;
import me.nehlsen.webapitester.run.executor.http.HttpTaskExecutor;
import org.springframework.stereotype.Component;

import java.net.http.HttpRequest;

@Component
public class HttpGetTaskExecutor extends HttpTaskExecutor {

    public HttpGetTaskExecutor(
            HttpClientFactory httpClientFactory,
            HttpRequestResponseMapper requestResponseMapper,
            RequestBodyFactory requestBodyFactory
    ) {
        super(httpClientFactory, requestResponseMapper, requestBodyFactory);
    }

    @Override
    public boolean supports(TaskDto task) {
        return task instanceof HttpGetTaskDto;
    }

    @Override
    protected String requestMethod() {
        return "GET";
    }
}
