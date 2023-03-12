package ru.dargen.rest.proxy.executor;

import ru.dargen.rest.proxy.Endpoint;
import ru.dargen.rest.client.RestClient;

public class ResponseExecutor extends AbstractExecutor {

    public ResponseExecutor(Endpoint endpoint, RestClient client, Class<?> responseType) {
        super(endpoint, client, responseType);
    }

}
