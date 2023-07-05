package me.nehlsen.webapitester.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FunctionCallStopwatchTest {
    @Test
    public void run_returns_result_of_encapsulated_method() {
        final FunctionCallStopwatch<String> stopwatch = new FunctionCallStopwatch<>();
        final String runResult = stopwatch.run(() -> "some new string");
        assertThat(runResult).isEqualTo("some new string");
    }

    @Test
    public void run_tracks_time_of_function_call() {
        final FunctionCallStopwatch<String> stopwatch = new FunctionCallStopwatch<>();
        stopwatch.run(() -> {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            return "something";
        });

        assertThat(stopwatch.getTimeMillis()).isGreaterThan(0L);
    }
}