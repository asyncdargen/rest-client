package ru.dargen.rest.proxy.executor;

import lombok.val;
import ru.dargen.rest.client.RestClient;
import ru.dargen.rest.proxy.Endpoint;
import ru.dargen.rest.request.Request;

public class ResponseBodyExecutor extends AbstractExecutor {

    public ResponseBodyExecutor(Endpoint endpoint, RestClient client, Class<?> responseType) {
        super(endpoint, client, responseType);
    }

    @Override
    public Object execute(Request request) {
        val response = super.executeRequest(request);
        response.rethrow();

        return response.getBody();
    }

}
