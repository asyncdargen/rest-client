package ru.dargen.rest.client;

import ru.dargen.rest.response.Response;
import ru.dargen.rest.serializer.BodyAdapter;

import java.nio.charset.StandardCharsets;

public class TransformBodyStrategy {

    @SuppressWarnings("unchecked")
    public static <T> Response<T> transform(Response<byte[]> response, Class<T> type, BodyAdapter adapter) {
        if (type == byte[].class)
            return (Response<T>) response;
        else if (type == String.class)
            return (Response<T>) response.withTransformedBody(body -> new String(body, StandardCharsets.UTF_8));
        else return response.withTransformedBody(body -> adapter.deserialize(body, type));
    }

}
