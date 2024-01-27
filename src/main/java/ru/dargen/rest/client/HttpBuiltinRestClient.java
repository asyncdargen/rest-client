package ru.dargen.rest.client;

import lombok.SneakyThrows;
import lombok.val;
import ru.dargen.rest.request.Request;
import ru.dargen.rest.request.RequestOption;
import ru.dargen.rest.response.Response;
import ru.dargen.rest.response.ResponseStatus;
import ru.dargen.rest.serializer.BodyAdapter;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class HttpBuiltinRestClient extends AbstractRestClient {

    public HttpBuiltinRestClient(BodyAdapter bodyAdapter) {
        super(bodyAdapter);
    }

    @Override
    @SneakyThrows
    Response<InputStream> execute(Request request) {
        ResponseStatus status = ResponseStatus.BAD_REQUEST;
        InputStream body = null;
        Map<String, String> headers = new HashMap<>();

        try {
            val connection = ((HttpURLConnection) new URL(request.getCompletedPath()).openConnection());

            connection.setConnectTimeout(request.getOption(RequestOption.REQUEST_TIMEOUT));
            connection.setReadTimeout(request.getOption(RequestOption.REQUEST_TIMEOUT));

            connection.setUseCaches(request.getOption(RequestOption.USE_CACHE));

            connection.setDoInput(true);
            connection.setDoOutput(true);

            connection.setRequestMethod(request.getMethod().name());

            request.getHeaders().forEach(connection::setRequestProperty);

            if (request.hasBody()) try (val out = connection.getOutputStream()) {
                out.write(bodyAdapter.serialize(request.getBody()));
                out.flush();
            }

            connection.getHeaderFields().forEach((key, value) -> headers.put(key, value.get(0)));

            status = ResponseStatus.getByCode(connection.getResponseCode());

            body = connection.getInputStream();
        } catch (Throwable throwable) {
            return new Response<>(status, headers, body, throwable);
        }

        return new Response<>(status, headers, body, null);
    }

}
