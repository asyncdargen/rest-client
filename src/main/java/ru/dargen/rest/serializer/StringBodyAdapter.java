package ru.dargen.rest.serializer;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

public interface StringBodyAdapter extends BodyAdapter {

    String serializeString(Object object);

    <T> T deserializeString(String rawString, Type type);

    @Override
    default byte[] serialize(Object object) {
        return serializeString(object).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    default <T> T deserialize(byte[] bytes, Type type) {
        return deserializeString(new String(bytes, StandardCharsets.UTF_8), type);
    }

}
