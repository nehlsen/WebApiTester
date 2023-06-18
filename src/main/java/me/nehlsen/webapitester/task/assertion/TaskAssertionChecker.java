package me.nehlsen.webapitester.task.assertion;

import me.nehlsen.webapitester.task.TaskExecutionContext;

public interface TaskAssertionChecker {

    boolean supports(TaskAssertion taskAssertion);

    CheckResult check(TaskAssertion taskAssertion, TaskExecutionContext context);
}
