package me.nehlsen.webapitester.api.assertion;

import me.nehlsen.webapitester.persistence.assertion.AssertionEntity;
import me.nehlsen.webapitester.persistence.assertion.AssertionEntityFactory;
import me.nehlsen.webapitester.persistence.assertion.RequestTimeAssertionEntity;
import me.nehlsen.webapitester.persistence.assertion.ResponseStatusCodeAssertionEntity;
import me.nehlsen.webapitester.persistence.assertion.UnknownAssertionTypeException;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

@Component
public class AssertionDtoFactory {

    public AssertionDto fromEntity(AssertionEntity assertion) {
        Objects.requireNonNull(assertion, "AssertionDtoFactory::fromEntity: requires non null AssertionEntity");

        return new AssertionDto(
                assertion.getUuid().toString(),
                assertionType(assertion),
                assertionParameters(assertion)
        );
    }

    private String assertionType(AssertionEntity assertion) {
        if (assertion instanceof RequestTimeAssertionEntity) {
            return AssertionEntityFactory.ASSERTION_REQUEST_TIME;
        }
        if (assertion instanceof ResponseStatusCodeAssertionEntity) {
            return AssertionEntityFactory.ASSERTION_RESPONSE_STATUS_CODE;
        }

        throw UnknownAssertionTypeException.ofTypeEntity(assertion);
    }

    private Map<String, String> assertionParameters(AssertionEntity assertion) {
        if (assertion instanceof RequestTimeAssertionEntity requestTimeAssertion) {
            return Map.of(AssertionEntityFactory.REQUEST_TIME_PARAM_NAME, String.valueOf(requestTimeAssertion.getMaximumRequestTimeMillis()));
        }
        if (assertion instanceof ResponseStatusCodeAssertionEntity responseStatusCodeAssertion) {
            return Map.of(AssertionEntityFactory.RESPONSE_STATUS_CODE_PARAM_NAME, String.valueOf(responseStatusCodeAssertion.getExpectedStatusCode()));
        }

        return Map.of();
    }
}
