package me.nehlsen.webapitester.run.dto;

import lombok.Data;

import java.net.URI;

@Data
abstract public class HttpTaskDto extends TaskDto {

    URI uri;
}
