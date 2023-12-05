package me.nehlsen.webapitester.run.executor;

import me.nehlsen.webapitester.run.dto.HttpPostTaskDto;
import me.nehlsen.webapitester.run.dto.HttpRequestResponseMapper;
import me.nehlsen.webapitester.run.dto.TaskDto;
import me.nehlsen.webapitester.run.executor.http.HttpClientFactory;
import me.nehlsen.webapitester.run.executor.http.HttpTaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class HttpPostTaskExecutor extends HttpTaskExecutor {

    public HttpPostTaskExecutor(
            HttpClientFactory httpClientFactory,
            HttpRequestResponseMapper requestResponseMapper,
            RequestBodyFactory requestBodyFactory
    ) {
        super(httpClientFactory, requestResponseMapper, requestBodyFactory);
    }

    @Override
    public boolean supports(TaskDto task) {
        return task instanceof HttpPostTaskDto;
    }

    @Override
    protected String requestMethod() {
        return "POST";
    }
}
