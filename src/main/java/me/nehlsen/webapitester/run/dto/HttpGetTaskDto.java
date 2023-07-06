package me.nehlsen.webapitester.run.dto;

import lombok.Data;

import java.net.URI;

@Data
public class HttpGetTaskDto extends TaskDto {

    URI uri;

    // TODO assertions
}
