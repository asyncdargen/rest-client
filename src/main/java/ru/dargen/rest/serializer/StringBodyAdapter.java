package ru.dargen.rest.serializer;

import java.nio.charset.StandardCharsets;

public interface StringBodyAdapter extends BodyAdapter {

    String serializeString(Object object);

    <T> T deserializeString(String rawString, Class<T> type);

    default byte[] serialize(Object object) {
        return serializeString(object).getBytes(StandardCharsets.UTF_8);
    }

    default <T> T deserialize(byte[] bytes, Class<T> type) {
        return deserializeString(new String(bytes, StandardCharsets.UTF_8), type);
    }

}
