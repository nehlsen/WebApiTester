package me.nehlsen.webapitester.persistence.assertion;

import me.nehlsen.webapitester.api.assertion.CreateAssertionDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AssertionEntityFactoryTest {

    private AssertionEntityFactory factory;

    @BeforeEach
    void setUp() {
        factory = new AssertionEntityFactory();
    }

    @Test
    public void create_request_time_assertion() {
        CreateAssertionDto assertionDto = new CreateAssertionDto("request_time", Map.of("maximumRequestTimeMillis", "100"));

        final AssertionEntity assertionEntity = factory.newAssertion(assertionDto);
        assertThat(assertionEntity).isInstanceOf(RequestTimeAssertionEntity.class);
        RequestTimeAssertionEntity requestTimeAssertionEntity = (RequestTimeAssertionEntity) assertionEntity;
        assertThat(requestTimeAssertionEntity.getMaximumRequestTimeMillis()).isEqualTo(100);
    }

    @Test
    public void create_response_code_assertion() {
        CreateAssertionDto assertionDto = new CreateAssertionDto("response_status_code", Map.of("expectedStatusCode", "200"));

        final AssertionEntity assertionEntity = factory.newAssertion(assertionDto);
        assertThat(assertionEntity).isInstanceOf(ResponseStatusCodeAssertionEntity.class);
        ResponseStatusCodeAssertionEntity responseStatusCodeAssertionEntity = (ResponseStatusCodeAssertionEntity) assertionEntity;
        assertThat(responseStatusCodeAssertionEntity.getExpectedStatusCode()).isEqualTo(200);
    }

    @Test
    public void create_unknown_assertion() {
        CreateAssertionDto assertionDto = new CreateAssertionDto("unknown_assertion", Map.of());

        final UnknownAssertionTypeException unknownAssertionTypeException = assertThrows(UnknownAssertionTypeException.class, () -> factory.newAssertion(assertionDto));
        assertThat(unknownAssertionTypeException).hasMessage("Assertion Type \"unknown_assertion\" not supported");
    }
}
