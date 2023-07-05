package me.nehlsen.webapitester.api.assertion;

import me.nehlsen.webapitester.persistence.task.assertion.AssertionEntity;
import me.nehlsen.webapitester.persistence.task.assertion.RequestTimeAssertionEntity;
import me.nehlsen.webapitester.persistence.task.assertion.ResponseStatusCodeAssertionEntity;
import me.nehlsen.webapitester.persistence.task.assertion.UnknownAssertionTypeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AssertionDtoFactoryTest {

    private AssertionDtoFactory assertionDtoFactory;

    @BeforeEach
    void setUp() {
        assertionDtoFactory = new AssertionDtoFactory();
    }

    @Test
    void create_from_request_time_assertion_entity() {
        RequestTimeAssertionEntity entity = new RequestTimeAssertionEntity();
        entity.setUuid(UUID.fromString("af70b1d3-ad41-4d9f-b44a-f3cc7524d142"));
        entity.setMaximumRequestTimeMillis(333);

        final AssertionDto assertionDto = assertionDtoFactory.fromEntity(entity);
        assertThat(assertionDto.getUuid()).isEqualTo(entity.getUuid().toString());
        assertThat(assertionDto.getType()).isEqualTo("request_time");
        assertThat(assertionDto.getParameters()).hasSize(1);
        assertThat(assertionDto.getParameters()).contains(entry("maximumRequestTimeMillis", "333"));
    }

    @Test
    void create_from_response_code_assertion_entity() {
        ResponseStatusCodeAssertionEntity entity = new ResponseStatusCodeAssertionEntity();
        entity.setUuid(UUID.fromString("af70b1d3-ad41-4d9f-b44a-f3cc7524d142"));
        entity.setExpectedStatusCode(220);

        final AssertionDto assertionDto = assertionDtoFactory.fromEntity(entity);
        assertThat(assertionDto.getUuid()).isEqualTo(entity.getUuid().toString());
        assertThat(assertionDto.getType()).isEqualTo("response_status_code");
        assertThat(assertionDto.getParameters()).hasSize(1);
        assertThat(assertionDto.getParameters()).contains(entry("expectedStatusCode", "220"));
    }

    @Test
    void create_from_unknown_assertion_entity() {
        UUID mockUuid = Mockito.mock(UUID.class);
        Mockito.when(mockUuid.toString()).thenReturn("faked mock UUID");

        AssertionEntity entity = Mockito.mock(AssertionEntity.class);
        Mockito.when(entity.getUuid()).thenReturn(mockUuid);

        final UnknownAssertionTypeException unknownAssertionTypeException = assertThrows(UnknownAssertionTypeException.class, () -> assertionDtoFactory.fromEntity(entity));
        assertThat(unknownAssertionTypeException).hasMessageContaining("Assertion Type not supported");
    }
}
