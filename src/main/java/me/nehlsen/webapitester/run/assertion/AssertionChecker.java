package me.nehlsen.webapitester.run.assertion;

import me.nehlsen.webapitester.run.context.TaskExecutionContext;
import me.nehlsen.webapitester.run.dto.AssertionDto;

public interface AssertionChecker {
    boolean supports(AssertionDto assertion);

    AssertionResult check(AssertionDto assertion, TaskExecutionContext context);
}
