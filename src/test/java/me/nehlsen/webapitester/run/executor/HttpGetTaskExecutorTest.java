package me.nehlsen.webapitester.run.executor;

import me.nehlsen.webapitester.run.dto.HttpGetTaskDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpGetTaskExecutorTest extends HttpTaskExecutorTest {

    @BeforeEach
    void setUp() {
        super.setUp();
        httpTaskExecutor = new HttpGetTaskExecutor(httpClientFactory, httpRequestResponseMapper);
    }

    @Test
    public void it_supports_HttpGetTaskDto() {
        assertThat(httpTaskExecutor.supports(new HttpGetTaskDto())).isTrue();
    }
}
