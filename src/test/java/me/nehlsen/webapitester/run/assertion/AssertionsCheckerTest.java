package me.nehlsen.webapitester.run.assertion;

import me.nehlsen.webapitester.run.context.TaskExecutionContext;
import me.nehlsen.webapitester.run.dto.AssertionDto;
import me.nehlsen.webapitester.run.dto.TaskDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AssertionsCheckerTest {

    @Test
    public void it_does_not_add_results_if_it_has_no_assertions() {
        final TaskExecutionContext taskExecutionContext = createTaskExecutionContextMock(List.of());

        final AssertionsChecker checker = new AssertionsChecker(List.of());
        checker.check(taskExecutionContext);

        verify(taskExecutionContext, never()).addAssertionResults(any(), any());
    }

    @Test
    public void it_adds_empty_result_list_if_it_has_no_checkers() {
        final AssertionDto assertionDto = mock(AssertionDto.class);
        final TaskExecutionContext taskExecutionContext = createTaskExecutionContextMock(List.of(assertionDto));

        final AssertionsChecker checker = new AssertionsChecker(List.of());
        checker.check(taskExecutionContext);

        verify(taskExecutionContext, atLeastOnce()).addAssertionResults(assertionDto, List.of());
    }

    @Test
    public void it_adds_result_if_it_has_assertion_and_checker() {
        final AssertionResult assertionResult = mock(AssertionResult.class);
        final AssertionChecker assertionChecker = createAssertionCheckerMock(assertionResult);

        final AssertionDto assertionDto = mock(AssertionDto.class);
        final TaskExecutionContext taskExecutionContext = createTaskExecutionContextMock(List.of(assertionDto));

        final AssertionsChecker checker = new AssertionsChecker(List.of(assertionChecker));
        checker.check(taskExecutionContext);

        verify(assertionChecker, atLeastOnce()).supports(any());
        verify(taskExecutionContext, atLeastOnce()).addAssertionResults(assertionDto, List.of(assertionResult));
    }

    private static AssertionChecker createAssertionCheckerMock(AssertionResult assertionResult) {
        final AssertionChecker assertionChecker = mock(AssertionChecker.class);
        when(assertionChecker.supports(any())).thenReturn(true);
        when(assertionChecker.check(any(), any())).thenReturn(assertionResult);

        return assertionChecker;
    }

    private static TaskExecutionContext createTaskExecutionContextMock(List<AssertionDto> assertionDtos) {
        final TaskDto taskDto = mock(TaskDto.class);
        when(taskDto.getAssertions()).thenReturn(assertionDtos);

        final TaskExecutionContext taskExecutionContext = mock(TaskExecutionContext.class);
        when(taskExecutionContext.getTask()).thenReturn(taskDto);

        return taskExecutionContext;
    }
}
