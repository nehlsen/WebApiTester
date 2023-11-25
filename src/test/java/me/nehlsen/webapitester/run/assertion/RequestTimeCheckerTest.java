package me.nehlsen.webapitester.run.assertion;

import me.nehlsen.webapitester.run.context.TaskExecutionContext;
import me.nehlsen.webapitester.run.dto.AssertionDto;
import me.nehlsen.webapitester.run.dto.HttpResponseDto;
import me.nehlsen.webapitester.run.dto.RequestTimeAssertionDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class RequestTimeCheckerTest {

    private RequestTimeChecker requestTimeChecker;

    @BeforeEach
    void setUp() {
        requestTimeChecker = new RequestTimeChecker();
    }

    @Test
    public void it_supports_instances_of_RequestTime() {
        RequestTimeAssertionDto taskAssertion = new RequestTimeAssertionDto();
        assertThat(requestTimeChecker.supports(taskAssertion)).isTrue();
    }

    @Test
    public void it_does_not_support_instances_not_of_RequestTime() {
        AssertionDto taskAssertion = mock(AssertionDto.class);
        assertThat(requestTimeChecker.supports(taskAssertion)).isFalse();
    }

    @Test
    public void it_does_not_support_null_as_RequestTime() {
        AssertionDto taskAssertion = null;
        assertThat(requestTimeChecker.supports(taskAssertion)).isFalse();
    }

    @Test
    public void check_result_is_positive_if_actual_request_time_less_than_max_request_time() {
        final long actualRequestTime = 145;
        final long maxRequestTime = 200;

        final HttpResponseDto responseDto = mock(HttpResponseDto.class);
        Mockito.when(responseDto.getResponseTimeMillis()).thenReturn(actualRequestTime);

        TaskExecutionContext context = mock(TaskExecutionContext.class);
        Mockito.when(context.getResponse()).thenReturn(responseDto);

        RequestTimeAssertionDto taskAssertion = new RequestTimeAssertionDto();
        taskAssertion.setMaximumRequestTimeMillis(maxRequestTime);
        assertThat(requestTimeChecker.check(taskAssertion, context).isPositive()).isTrue();
    }

    @Test
    public void check_result_is_negative_if_actual_request_time_equal_to_max_request_time() {
        final long actualRequestTime = 145;
        final long maxRequestTime = actualRequestTime;

        final HttpResponseDto responseDto = mock(HttpResponseDto.class);
        Mockito.when(responseDto.getResponseTimeMillis()).thenReturn(actualRequestTime);

        TaskExecutionContext context = mock(TaskExecutionContext.class);
        Mockito.when(context.getResponse()).thenReturn(responseDto);

        RequestTimeAssertionDto taskAssertion = new RequestTimeAssertionDto();
        taskAssertion.setMaximumRequestTimeMillis(maxRequestTime);
        assertThat(requestTimeChecker.check(taskAssertion, context).isPositive()).isFalse();
    }

    @Test
    public void check_result_is_negative_if_actual_request_time_greater_than_max_request_time() {
        final long actualRequestTime = 333;
        final long maxRequestTime = 200;

        final HttpResponseDto responseDto = mock(HttpResponseDto.class);
        Mockito.when(responseDto.getResponseTimeMillis()).thenReturn(actualRequestTime);

        TaskExecutionContext context = mock(TaskExecutionContext.class);
        Mockito.when(context.getResponse()).thenReturn(responseDto);

        RequestTimeAssertionDto taskAssertion = new RequestTimeAssertionDto();
        taskAssertion.setMaximumRequestTimeMillis(maxRequestTime);
        assertThat(requestTimeChecker.check(taskAssertion, context).isPositive()).isFalse();
    }
}
