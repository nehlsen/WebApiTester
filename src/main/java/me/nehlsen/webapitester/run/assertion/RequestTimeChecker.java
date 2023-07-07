package me.nehlsen.webapitester.run.assertion;

import lombok.extern.log4j.Log4j2;
import me.nehlsen.webapitester.run.context.TaskExecutionContext;
import me.nehlsen.webapitester.run.dto.AssertionDto;
import me.nehlsen.webapitester.run.dto.RequestTimeAssertionDto;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class RequestTimeChecker implements AssertionChecker {

    @Override
    public boolean supports(AssertionDto assertion) {
        return assertion instanceof RequestTimeAssertionDto;
    }

    @Override
    public AssertionResult check(AssertionDto assertion, TaskExecutionContext context) {
        final RequestTimeAssertionDto requestTimeAssertion = (RequestTimeAssertionDto) assertion;

        log.info(
                "check ({}ms <? {}ms)",
                context.getRequestTimeMillis(),
                requestTimeAssertion.getMaximumRequestTimeMillis()
        );

        return new AssertionResult(
                context.getRequestTimeMillis() < requestTimeAssertion.getMaximumRequestTimeMillis()
        );
    }
}
