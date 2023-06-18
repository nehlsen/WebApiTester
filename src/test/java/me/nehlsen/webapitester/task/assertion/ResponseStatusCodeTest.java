package me.nehlsen.webapitester.task.assertion;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ResponseStatusCodeTest {
    @Test
    public void getter_works() {
        ResponseStatusCode responseStatusCode = new ResponseStatusCode(123);
        assertThat(responseStatusCode.getExpectedStatusCode()).isEqualTo(123);
    }
}
