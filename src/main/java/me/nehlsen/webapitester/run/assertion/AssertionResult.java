package me.nehlsen.webapitester.run.assertion;

import lombok.Getter;

public class AssertionResult {
    @Getter
    boolean positive;

    public AssertionResult(boolean positive) {
        this.positive = positive;
    }
}
