package me.nehlsen.webapitester.task.assertion;

import me.nehlsen.webapitester.task.TaskExecutionContext;
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
        ResponseStatusCode taskAssertion = new ResponseStatusCode(0);
        assertThat(responseStatusCodeChecker.supports(taskAssertion)).isTrue();
    }

    @Test
    public void it_does_not_support_instances_not_of_ResponseStatusCode() {
        TaskAssertion taskAssertion = Mockito.mock(TaskAssertion.class);
        assertThat(responseStatusCodeChecker.supports(taskAssertion)).isFalse();
    }

    @Test
    public void it_does_not_support_null_as_ResponseStatusCode() {
        TaskAssertion taskAssertion = null;
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

        ResponseStatusCode taskAssertion = new ResponseStatusCode(expectedStatusCode);
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

        ResponseStatusCode taskAssertion = new ResponseStatusCode(expectedStatusCode);
        assertThat(responseStatusCodeChecker.check(taskAssertion, context).isPositive()).isFalse();
    }
}
