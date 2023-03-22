package ru.dargen.rest.proxy.executor;

import lombok.val;
import ru.dargen.rest.client.RestClient;
import ru.dargen.rest.proxy.Endpoint;
import ru.dargen.rest.request.Request;

import java.lang.reflect.Type;

public class ResponseBodyExecutor extends AbstractExecutor {

    public ResponseBodyExecutor(Endpoint endpoint, RestClient client, Type responseType) {
        super(endpoint, client, responseType);
    }

    @Override
    public Object execute(Request request) {
        val response = super.executeRequest(request);
        response.rethrow();

        return response.getBody();
    }

    @Override
    public String toString() {
        return "ResponseBodyExecutor[of " + responseType + "]";
    }
}
