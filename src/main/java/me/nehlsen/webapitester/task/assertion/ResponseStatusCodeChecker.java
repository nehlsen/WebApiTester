package me.nehlsen.webapitester.task.assertion;

import lombok.extern.log4j.Log4j2;
import me.nehlsen.webapitester.task.TaskExecutionContext;
import org.springframework.stereotype.Component;

@Component @Log4j2
public class ResponseStatusCodeChecker implements TaskAssertionChecker {
    @Override
    public boolean supports(TaskAssertion taskAssertion) {
        return taskAssertion instanceof ResponseStatusCode;
    }

    @Override
    public CheckResult check(TaskAssertion taskAssertion, TaskExecutionContext context) {
        ResponseStatusCode responseCodeAssertion = (ResponseStatusCode) taskAssertion;

        log.info(
                "check ({} ==? {})",
                responseCodeAssertion.getExpectedStatusCode(),
                context.getResponse().statusCode()
        );

        return new CheckResult(
                responseCodeAssertion.getExpectedStatusCode() == context.getResponse().statusCode()
        );
    }
}
