package me.nehlsen.webapitester.task.assertion;

import lombok.Getter;

public class CheckResult {
    @Getter
    boolean positive;

    public CheckResult(boolean positive) {
        this.positive = positive;
    }
}
