package ru.dargen.rest.client;

import lombok.SneakyThrows;
import lombok.val;
import lombok.var;
import ru.dargen.rest.request.Request;
import ru.dargen.rest.serializer.BodyAdapter;
import ru.dargen.rest.response.Response;
import ru.dargen.rest.response.ResponseStatus;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpBuiltinRestClient extends AbstractRestClient {

    public HttpBuiltinRestClient(BodyAdapter bodyAdapter) {
        super(bodyAdapter);
    }

    @Override
    @SneakyThrows
    Response<byte[]> execute(Request request) {
        ResponseStatus status = null;
        byte[] body = new byte[0];

        try {
            val connection = ((HttpURLConnection) new URL(request.getCompletedPath()).openConnection());

            connection.setDoInput(true);
            connection.setDoOutput(true);

            connection.setRequestMethod(request.getMethod().name());

            request.getHeaders().forEach(connection::setRequestProperty);

            if (request.hasBody()) try (val out = connection.getOutputStream()) {
                out.write(bodyAdapter.serialize(request.getBody()));
                out.flush();
            }

            status = ResponseStatus.getByCode(connection.getResponseCode());

            try (val input = connection.getInputStream()) {
                body = readAllBytes(input);
            }
        } catch (Throwable throwable) {
            return new Response<>(status, body, throwable);
        }

        return new Response<>(status, body, null);
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
        }
    }

}
