package me.nehlsen.webapitester.api.assertion;

import lombok.Value;

import java.util.Map;
import java.util.Optional;

@Value
public class CreateAssertionDto {
    String type;
    Map<String, String> parameters;

    public String getParameter(String name) {
        return Optional.ofNullable(getParameters().get(name)).orElseThrow(() -> AssertionParameterMissingException.byName(name));
    }

    public Long getLongParameter(String name) {
        return Long.parseLong(getParameter(name));
    }

    public Integer getIntegerParameter(String name) {
        return Integer.parseInt(getParameter(name));
    }
}
