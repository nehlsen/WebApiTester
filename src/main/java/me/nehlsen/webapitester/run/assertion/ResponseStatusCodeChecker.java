package me.nehlsen.webapitester.run.assertion;

import lombok.extern.log4j.Log4j2;
import me.nehlsen.webapitester.run.context.TaskExecutionContext;
import me.nehlsen.webapitester.run.dto.AssertionDto;
import me.nehlsen.webapitester.run.dto.ResponseStatusCodeAssertionDto;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class ResponseStatusCodeChecker implements AssertionChecker {

    @Override
    public boolean supports(AssertionDto assertion) {
        return assertion instanceof ResponseStatusCodeAssertionDto;
    }

    @Override
    public AssertionResult check(AssertionDto assertion, TaskExecutionContext context) {
        final ResponseStatusCodeAssertionDto responseStatusCodeAssertion = (ResponseStatusCodeAssertionDto) assertion;

        log.info(
                "check ({} ==? {})",
                responseStatusCodeAssertion.getExpectedStatusCode(),
                context.getResponse().getStatusCode()
        );

        return new AssertionResult(
                responseStatusCodeAssertion.getExpectedStatusCode() == context.getResponse().getStatusCode()
        );
    }
}
