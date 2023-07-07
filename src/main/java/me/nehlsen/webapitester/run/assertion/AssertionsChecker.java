package me.nehlsen.webapitester.run.assertion;

import lombok.extern.log4j.Log4j2;
import me.nehlsen.webapitester.run.context.TaskExecutionContext;
import me.nehlsen.webapitester.run.dto.AssertionDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component @Log4j2
public class AssertionsChecker {
    private final List<AssertionChecker> assertionCheckers;

    public AssertionsChecker(List<AssertionChecker> assertionCheckers) {
        this.assertionCheckers = assertionCheckers;
    }

    public void check(TaskExecutionContext context) {
        context.getTask().getAssertions().forEach(
                assertion -> context.addAssertionResults(assertion, checkAssertion(assertion, context))
        );
    }

    private List<AssertionResult> checkAssertion(AssertionDto assertion, TaskExecutionContext context) {
        log.info("checkAssertion");

        return assertionCheckers.stream()
                .filter(checker -> checker.supports(assertion))
                .map(checker -> checker.check(assertion, context))
                .toList();
    }
}
