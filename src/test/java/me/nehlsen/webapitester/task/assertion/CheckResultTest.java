package me.nehlsen.webapitester.task.assertion;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CheckResultTest {
    @Test
    public void getter_works() {
        CheckResult checkResult = new CheckResult(true);
        assertThat(checkResult.isPositive()).isTrue();
    }
}
