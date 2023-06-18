package me.nehlsen.webapitester.task.assertion;

import lombok.Getter;

public class RequestTime implements TaskAssertion {
    @Getter
    long maximumRequestTimeMillis;

    public RequestTime(long maximumRequestTimeMillis) {
        this.maximumRequestTimeMillis = maximumRequestTimeMillis;
    }
}
