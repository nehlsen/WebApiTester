package me.nehlsen.webapitester.api;

import lombok.Value;

@Value
public class ScheduleResponse {
    boolean success;
    String message;
}
