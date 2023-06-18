package me.nehlsen.webapitester.task.assertion;

import lombok.Getter;

public class ResponseStatusCode implements TaskAssertion {
    @Getter
    private final int expectedStatusCode;

    public ResponseStatusCode(int expectedStatusCode) {
        this.expectedStatusCode = expectedStatusCode;
    }
}
