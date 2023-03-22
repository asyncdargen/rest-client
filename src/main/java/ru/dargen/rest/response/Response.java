package ru.dargen.rest.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.ToString;

import java.util.function.Function;

@Getter @ToString
@RequiredArgsConstructor
public class Response<T> {

    private final ResponseStatus status;
    private final T body;
    private final Throwable throwable;

    public boolean isThrows() {
        return throwable != null;
    }

    @SneakyThrows
    public void rethrow() {
        if (isThrows())
            throw new ResponseException(status, throwable);
    }

    public <B> Response<B> withTransformedBody(Function<T, B> transformer) {
        return isThrows()
                ? new Response<>(status, null, throwable)
                : new Response<>(status, transformer.apply(body), throwable);
    }

}
