package ru.dargen.rest.response;

import lombok.Getter;

@Getter
public class ResponseException extends RuntimeException {

    private final ResponseStatus status;

    public ResponseException(ResponseStatus status, Throwable throwable) {
        super(status.toString(), throwable);
        this.status = status;
    }

}
