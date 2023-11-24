package me.nehlsen.webapitester.run.executor;

import me.nehlsen.webapitester.run.context.TaskExecutionContext;
import me.nehlsen.webapitester.run.dto.VoidTaskDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class VoidTaskExecutorTest {
    @Test
    public void it_supports_VoidTaskDto() {
        final VoidTaskExecutor voidTaskExecutor = new VoidTaskExecutor();
        assertThat(voidTaskExecutor.supports(new VoidTaskDto())).isTrue();
    }

    @Test
    public void it_executes_without_exception() {
        final VoidTaskExecutor voidTaskExecutor = new VoidTaskExecutor();
        voidTaskExecutor.execute(mock(TaskExecutionContext.class));
    }
}