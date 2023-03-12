package ru.dargen.rest.client;

import lombok.SneakyThrows;
import lombok.val;
import ru.dargen.rest.request.Request;
import ru.dargen.rest.response.Response;
import ru.dargen.rest.response.ResponseStatus;
import ru.dargen.rest.serializer.BodyAdapter;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpBuiltinRestClient extends AbstractRestClient {

    public HttpBuiltinRestClient(BodyAdapter bodyAdapter) {
        super(bodyAdapter);
    }

    @Override
    @SneakyThrows
    Response<InputStream> execute(Request request) {
        ResponseStatus status = null;
        InputStream body = null;

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

            body = connection.getInputStream();
        } catch (Throwable throwable) {
            return new Response<>(status, body, throwable);
        }

        return new Response<>(status, body, null);
    }

}
