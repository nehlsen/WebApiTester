package me.nehlsen.webapitester.run.assertion;

import me.nehlsen.webapitester.run.context.TaskExecutionContext;
import me.nehlsen.webapitester.run.dto.AssertionDto;
import me.nehlsen.webapitester.run.dto.ResponseStatusCodeAssertionDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.net.http.HttpResponse;

import static org.assertj.core.api.Assertions.*;

class ResponseStatusCodeCheckerTest {

    private ResponseStatusCodeChecker responseStatusCodeChecker;

    @BeforeEach
    void setUp() {
        responseStatusCodeChecker = new ResponseStatusCodeChecker();
    }

    @Test
    public void it_supports_instances_of_ResponseStatusCode() {
        ResponseStatusCodeAssertionDto taskAssertion = new ResponseStatusCodeAssertionDto();
        assertThat(responseStatusCodeChecker.supports(taskAssertion)).isTrue();
    }

    @Test
    public void it_does_not_support_instances_not_of_ResponseStatusCode() {
        AssertionDto taskAssertion = Mockito.mock(AssertionDto.class);
        assertThat(responseStatusCodeChecker.supports(taskAssertion)).isFalse();
    }

    @Test
    public void it_does_not_support_null_as_ResponseStatusCode() {
        AssertionDto taskAssertion = null;
        assertThat(responseStatusCodeChecker.supports(taskAssertion)).isFalse();
    }

    @Test
    public void check_result_is_positive_if_code_matches() {
        final int expectedStatusCode = 222;

        @SuppressWarnings("unchecked")
        HttpResponse<String> httpResponse = (HttpResponse<String>)Mockito.mock(HttpResponse.class);
        Mockito.when(httpResponse.statusCode()).thenReturn(expectedStatusCode);

        TaskExecutionContext context = Mockito.mock(TaskExecutionContext.class);
        Mockito.when(context.getResponse()).thenReturn(httpResponse);

        ResponseStatusCodeAssertionDto taskAssertion = new ResponseStatusCodeAssertionDto();
        taskAssertion.setExpectedStatusCode(expectedStatusCode);
        assertThat(responseStatusCodeChecker.check(taskAssertion, context).isPositive()).isTrue();
    }

    @Test
    public void check_result_is_negative_if_code_not_matches() {
        final int actualStatusCode = 444;
        final int expectedStatusCode = 222;

        @SuppressWarnings("unchecked")
        HttpResponse<String> httpResponse = (HttpResponse<String>)Mockito.mock(HttpResponse.class);
        Mockito.when(httpResponse.statusCode()).thenReturn(actualStatusCode);

        TaskExecutionContext context = Mockito.mock(TaskExecutionContext.class);
        Mockito.when(context.getResponse()).thenReturn(httpResponse);

        ResponseStatusCodeAssertionDto taskAssertion = new ResponseStatusCodeAssertionDto();
        taskAssertion.setExpectedStatusCode(expectedStatusCode);
        assertThat(responseStatusCodeChecker.check(taskAssertion, context).isPositive()).isFalse();
    }
}