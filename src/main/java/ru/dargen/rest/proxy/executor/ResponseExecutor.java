package ru.dargen.rest.proxy.executor;

import ru.dargen.rest.proxy.Endpoint;
import ru.dargen.rest.client.RestClient;

import java.lang.reflect.Type;

public class ResponseExecutor extends AbstractExecutor {

    public ResponseExecutor(Endpoint endpoint, RestClient client, Type responseType) {
        super(endpoint, client, responseType);
    }

    @Override
    public String toString() {
        return "ResponseExecutor[of " + responseType + "]";
    }

}
