package ru.dargen.rest.client;

import lombok.SneakyThrows;
import lombok.val;
import ru.dargen.rest.response.Response;
import ru.dargen.rest.serializer.BodyAdapter;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Type;

import static java.nio.charset.StandardCharsets.UTF_8;

public class TransformBodyStrategy {

    @SuppressWarnings("unchecked")
    public static <T> Response<T> transform(Response<InputStream> response, Type type, BodyAdapter adapter) {
        if (type == InputStream.class) {
            return (Response<T>) response;
        } else if (type == byte[].class) {
            return (Response<T>) response.withTransformedBody(TransformBodyStrategy::readAllBytes);
        } else if (type == String.class) {
            return (Response<T>) response.withTransformedBody(body -> new String(readAllBytes(body), UTF_8));
        }

        return response.withTransformedBody(body -> adapter.deserialize(readAllBytes(body), type));
    }

    @SneakyThrows
    protected static byte[] readAllBytes(InputStream inputStream) {
        val buffer = new byte[2048];
        var size = 0;

        try (val baos = new ByteArrayOutputStream()) {
            while ((size = inputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, size);
            }

            return baos.toByteArray();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return new byte[0];
        } finally {
            inputStream.close();
        }
    }

}
