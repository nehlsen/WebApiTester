package me.nehlsen.webapitester.run.executor;

import me.nehlsen.webapitester.run.context.TaskExecutionContext;
import me.nehlsen.webapitester.run.dto.TaskDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RequestBodyFactoryTest {

    private RequestBodyFactory requestBodyFactory;

    @BeforeEach
    void setUp() {
        requestBodyFactory = new RequestBodyFactory();
    }

    @Test
    public void it_returns_an_empty_body_if_neither_body_nor_expression_is_defined() {
        final TaskDto taskDto = mock(TaskDto.class);
        when(taskDto.getParameters()).thenReturn(Map.of());

        final TaskExecutionContext taskExecutionContext = mock(TaskExecutionContext.class);
        when(taskExecutionContext.getTask()).thenReturn(taskDto);

        assertThat(requestBodyFactory.buildBody(taskExecutionContext)).isEqualTo("");
    }

    @Test
    public void it_returns_body_if_no_expression_is_defined() {
        final TaskDto taskDto = mock(TaskDto.class);
        when(taskDto.getParameters()).thenReturn(Map.of("body", "some body"));

        final TaskExecutionContext taskExecutionContext = mock(TaskExecutionContext.class);
        when(taskExecutionContext.getTask()).thenReturn(taskDto);

        assertThat(requestBodyFactory.buildBody(taskExecutionContext)).isEqualTo("some body");
    }

    @Test
    public void it_evaluates_expression_if_expression_is_defined() {
        final TaskDto taskDto = mock(TaskDto.class);
        when(taskDto.getParameters()).thenReturn(Map.of(
                "body_expression", "'hello expression'.toUpperCase()",
                "body", "should be ignored"
        ));

        final TaskExecutionContext taskExecutionContext = mock(TaskExecutionContext.class);
        when(taskExecutionContext.getTask()).thenReturn(taskDto);

        assertThat(requestBodyFactory.buildBody(taskExecutionContext)).isEqualTo("HELLO EXPRESSION");
    }

    @Test
    public void expression_can_access_context() {
        final TaskDto taskDto = mock(TaskDto.class);
        when(taskDto.getParameters()).thenReturn(Map.of(
                "body_expression", "context.task.name"
        ));
        when(taskDto.getName()).thenReturn("the name of the task");

        final TaskExecutionContext taskExecutionContext = mock(TaskExecutionContext.class);
        when(taskExecutionContext.getTask()).thenReturn(taskDto);

        assertThat(requestBodyFactory.buildBody(taskExecutionContext)).isEqualTo("the name of the task");
    }
}
