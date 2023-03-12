package ru.dargen.rest.proxy.executor;

import ru.dargen.rest.client.RestClient;
import ru.dargen.rest.proxy.Endpoint;
import ru.dargen.rest.request.Request;

public class VoidExecutor extends AbstractExecutor {

    public VoidExecutor(Endpoint endpoint, RestClient client) {
        super(endpoint, client, byte[].class);
    }

    @Override
    public Object execute(Request request) {
        super.executeRequest(request).rethrow();

        return null;
    }

}
