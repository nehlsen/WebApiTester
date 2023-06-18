package me.nehlsen.webapitester.task.assertion;

import lombok.extern.log4j.Log4j2;
import me.nehlsen.webapitester.task.TaskExecutionContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component @Log4j2
public class TaskAssertionsChecker {
    private final List<TaskAssertionChecker> taskAssertionCheckers;

    public TaskAssertionsChecker(List<TaskAssertionChecker> taskAssertionCheckers) {
        this.taskAssertionCheckers = taskAssertionCheckers;
    }

    public void check(TaskExecutionContext context) {
        context.getTask().getAssertions().forEach(
                taskAssertion -> context.addResults(taskAssertion, checkAssertion(taskAssertion, context))
        );
    }

    private List<CheckResult> checkAssertion(TaskAssertion assertion, TaskExecutionContext context) {
        log.info("checkAssertion");

        return taskAssertionCheckers.stream()
                .filter(checker -> checker.supports(assertion))
                .map(checker -> checker.check(assertion, context))
                .toList();
    }
}
