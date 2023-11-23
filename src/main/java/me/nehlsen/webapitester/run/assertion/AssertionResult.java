package me.nehlsen.webapitester.run.assertion;

import lombok.Getter;

@Getter
public class AssertionResult {
    boolean positive;

    public AssertionResult(boolean positive) {
        this.positive = positive;
    }
}
