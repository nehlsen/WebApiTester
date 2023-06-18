package me.nehlsen.webapitester.task.assertion;

import lombok.extern.log4j.Log4j2;
import me.nehlsen.webapitester.task.TaskExecutionContext;
import org.springframework.stereotype.Component;

@Component @Log4j2
public class RequestTimeChecker implements TaskAssertionChecker {

    @Override
    public boolean supports(TaskAssertion taskAssertion) {
        return taskAssertion instanceof RequestTime;
    }

    @Override
    public CheckResult check(TaskAssertion taskAssertion, TaskExecutionContext context) {
        RequestTime requestTimeAssertion = (RequestTime) taskAssertion;

        log.info(
                "check ({}ms <? {}ms)",
                requestTimeAssertion.getMaximumRequestTimeMillis(),
                context.getRequestTimeMillis()
        );

        return new CheckResult(
                requestTimeAssertion.getMaximumRequestTimeMillis() < context.getRequestTimeMillis()
        );
    }
}
