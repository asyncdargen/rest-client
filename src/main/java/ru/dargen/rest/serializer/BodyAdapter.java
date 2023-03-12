package ru.dargen.rest.serializer;

public interface BodyAdapter {

    byte[] serialize(Object object);

    <T> T deserialize(byte[] bytes, Class<T> type);

}
