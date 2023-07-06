package me.nehlsen.webapitester.run.executor;

import me.nehlsen.webapitester.run.dto.HttpGetTaskDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpGetTaskExecutorTest {
    @Test
    public void it_supports_HttpGetTaskDto() {
        final HttpGetTaskExecutor httpGetTaskExecutor = new HttpGetTaskExecutor();
        assertThat(httpGetTaskExecutor.supports(new HttpGetTaskDto())).isTrue();
    }
}