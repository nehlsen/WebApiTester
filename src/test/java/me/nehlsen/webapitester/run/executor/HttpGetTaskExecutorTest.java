package me.nehlsen.webapitester.run.executor;

import me.nehlsen.webapitester.run.dto.HttpGetTaskDto;
import me.nehlsen.webapitester.run.dto.HttpRequestResponseMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class HttpGetTaskExecutorTest {
    @Test
    public void it_supports_HttpGetTaskDto() {
        final HttpGetTaskExecutor httpGetTaskExecutor = new HttpGetTaskExecutor(mock(HttpRequestResponseMapper.class));
        assertThat(httpGetTaskExecutor.supports(new HttpGetTaskDto())).isTrue();
    }
}