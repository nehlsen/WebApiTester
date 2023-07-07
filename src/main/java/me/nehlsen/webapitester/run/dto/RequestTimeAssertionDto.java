package me.nehlsen.webapitester.run.dto;

import lombok.Data;

@Data
public class RequestTimeAssertionDto extends AssertionDto {

    long maximumRequestTimeMillis;
}
