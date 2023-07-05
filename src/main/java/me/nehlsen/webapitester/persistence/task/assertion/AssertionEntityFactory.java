package me.nehlsen.webapitester.persistence.task.assertion;

import me.nehlsen.webapitester.api.assertion.CreateAssertionDto;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AssertionEntityFactory {

    public static final String ASSERTION_REQUEST_TIME = "request_time";
    public static final String ASSERTION_RESPONSE_STATUS_CODE = "response_status_code";

    // TODO move somewhere else
    public static final String REQUEST_TIME_PARAM_NAME = "maximumRequestTimeMillis";
    public static final String RESPONSE_STATUS_CODE_PARAM_NAME = "expectedStatusCode";

    public AssertionEntity newAssertion(CreateAssertionDto assertionDto) {
        Objects.requireNonNull(assertionDto, "AssertionEntityFactory::newAssertion: requires non null AssertionDto");

        AssertionEntity assertionEntity;

        if (assertionDto.getType().equals(ASSERTION_REQUEST_TIME)) {
            RequestTimeAssertionEntity requestTimeAssertionEntity = new RequestTimeAssertionEntity();
            requestTimeAssertionEntity.setMaximumRequestTimeMillis(assertionDto.getLongParameter(REQUEST_TIME_PARAM_NAME));

            assertionEntity = requestTimeAssertionEntity;
        } else if (assertionDto.getType().equals(ASSERTION_RESPONSE_STATUS_CODE)) {
            ResponseStatusCodeAssertionEntity responseStatusCodeAssertionEntity = new ResponseStatusCodeAssertionEntity();
            responseStatusCodeAssertionEntity.setExpectedStatusCode(assertionDto.getIntegerParameter(RESPONSE_STATUS_CODE_PARAM_NAME));

            assertionEntity = responseStatusCodeAssertionEntity;
        } else {
            throw UnknownAssertionTypeException.ofTypeString(assertionDto.getType());
        }

        return assertionEntity;
    }
}
