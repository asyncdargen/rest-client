package ru.dargen.rest.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor
public enum HttpMethod {

    GET(false),
    HEAD(false),
    POST(true),
    PUT(true),
    PATCH(true),
    DELETE(false),
    OPTIONS(false),
    TRACE(false),
    ;

    private final boolean hasBody;

}
