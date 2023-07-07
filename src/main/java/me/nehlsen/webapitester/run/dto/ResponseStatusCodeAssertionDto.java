package me.nehlsen.webapitester.run.dto;

import lombok.Data;

@Data
public class ResponseStatusCodeAssertionDto extends AssertionDto {

    int expectedStatusCode;
}
