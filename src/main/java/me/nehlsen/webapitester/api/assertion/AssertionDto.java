package me.nehlsen.webapitester.api.assertion;

import lombok.Value;

import java.util.Map;

@Value
public class AssertionDto {
    String uuid;
    String type;
    Map<String, String> parameters;
}
