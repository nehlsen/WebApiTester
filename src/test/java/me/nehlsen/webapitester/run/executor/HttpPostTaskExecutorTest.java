package me.nehlsen.webapitester.run.executor;

import me.nehlsen.webapitester.run.dto.HttpPostTaskDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class HttpPostTaskExecutorTest extends HttpTaskExecutorTest {
    @BeforeEach
    void setUp() {
        super.setUp();
        final RequestBodyFactory requestBodyFactory = mock(RequestBodyFactory.class);
        when(requestBodyFactory.buildBody(any())).thenReturn("the body of the request");
        httpTaskExecutor = new HttpPostTaskExecutor(httpClientFactory, httpRequestResponseMapper, requestBodyFactory);
    }

    @Test
    public void it_supports_HttpPostTaskDto() {
        assertThat(httpTaskExecutor.supports(new HttpPostTaskDto())).isTrue();
    }
}
