package me.nehlsen.webapitester.run.executor;

import me.nehlsen.webapitester.run.dto.HttpPostTaskDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpPostTaskExecutorTest extends HttpTaskExecutorTest {
    @BeforeEach
    void setUp() {
        super.setUp();
        httpTaskExecutor = new HttpPostTaskExecutor(httpClientFactory, httpRequestResponseMapper, requestBodyFactory);
    }

    @Test
    public void it_supports_HttpPostTaskDto() {
        assertThat(httpTaskExecutor.supports(new HttpPostTaskDto())).isTrue();
    }
}
