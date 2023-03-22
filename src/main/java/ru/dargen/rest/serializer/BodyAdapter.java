package ru.dargen.rest.serializer;

import java.lang.reflect.Type;

public interface BodyAdapter {

    byte[] serialize(Object object);

    <T> T deserialize(byte[] bytes, Type type);

}
