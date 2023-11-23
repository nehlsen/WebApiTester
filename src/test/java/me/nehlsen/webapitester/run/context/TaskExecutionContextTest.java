package me.nehlsen.webapitester.run.context;

import me.nehlsen.webapitester.run.assertion.AssertionResult;
import me.nehlsen.webapitester.run.dto.AssertionDto;
import me.nehlsen.webapitester.run.dto.TaskDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class TaskExecutionContextTest {

    @Test
    public void it_is_result_positive_with_empty_result_list() {
        final TaskDto taskDto = mock(TaskDto.class);
        final PlanExecutionContext planExecutionContext = mock(PlanExecutionContext.class);

        final TaskExecutionContext taskExecutionContext = new TaskExecutionContext(taskDto, planExecutionContext);

        assertThat(taskExecutionContext.getAssertionResults()).isEmpty();
        assertThat(taskExecutionContext.isResultPositive()).isTrue();
    }

    @Test
    public void it_is_result_positive_with_one_positive_result() {
        final TaskDto taskDto = mock(TaskDto.class);
        final PlanExecutionContext planExecutionContext = mock(PlanExecutionContext.class);

        final TaskExecutionContext taskExecutionContext = new TaskExecutionContext(taskDto, planExecutionContext);

        final AssertionDto assertionDto = mock(AssertionDto.class);
        taskExecutionContext.addAssertionResults(assertionDto, List.of(new AssertionResult(true)));

        assertThat(taskExecutionContext.getAssertionResults()).hasSize(1);
        assertThat(taskExecutionContext.isResultPositive()).isTrue();
    }

    @Test
    public void it_is_result_negative_with_one_negative_result() {
        final TaskDto taskDto = mock(TaskDto.class);
        final PlanExecutionContext planExecutionContext = mock(PlanExecutionContext.class);

        final TaskExecutionContext taskExecutionContext = new TaskExecutionContext(taskDto, planExecutionContext);

        final AssertionDto assertionDto = mock(AssertionDto.class);
        taskExecutionContext.addAssertionResults(assertionDto, List.of(new AssertionResult(false)));

        assertThat(taskExecutionContext.getAssertionResults()).hasSize(1);
        assertThat(taskExecutionContext.isResultPositive()).isFalse();
    }

    @Test
    public void it_is_result_negative_with_one_positive_and_one_negative_result() {
        final TaskDto taskDto = mock(TaskDto.class);
        final PlanExecutionContext planExecutionContext = mock(PlanExecutionContext.class);

        final TaskExecutionContext taskExecutionContext = new TaskExecutionContext(taskDto, planExecutionContext);

        final AssertionDto assertionDto = mock(AssertionDto.class);
        taskExecutionContext.addAssertionResults(assertionDto, List.of(new AssertionResult(true), new AssertionResult(false)));

        assertThat(taskExecutionContext.getAssertionResults()).hasSize(1);
        assertThat(taskExecutionContext.isResultPositive()).isFalse();
    }
}
