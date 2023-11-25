package me.nehlsen.webapitester.run.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Mapper(componentModel = ComponentModel.SPRING)
public interface HttpRequestResponseMapper {
    @Mapping(expression = "java(httpRequest.method())", target = "method")
    @Mapping(expression = "java(httpRequest.uri().toString())", target = "uri")
    @Mapping(expression = "java(httpRequest.headers().map())", target = "headers")
    HttpRequestDto toDto(HttpRequest httpRequest);

    @Mapping(expression = "java(httpResponse.statusCode())", target = "statusCode")
    @Mapping(expression = "java(httpResponse.headers().map())", target = "headers")
    HttpResponseDto toDto(HttpResponse<?> httpResponse);
}
