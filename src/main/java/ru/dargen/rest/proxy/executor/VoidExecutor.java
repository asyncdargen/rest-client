package ru.dargen.rest.proxy.executor;

import ru.dargen.rest.client.RestClient;
import ru.dargen.rest.proxy.Endpoint;
import ru.dargen.rest.request.Request;

public class VoidExecutor extends AbstractExecutor {

    protected final boolean async;

    public VoidExecutor(Endpoint endpoint, RestClient client, boolean async) {
        super(endpoint, client, byte[].class);
        this.async = async;
    }

    @Override
    public Object execute(Request request) {
        if (async) AsyncExecutor.EXECUTOR.execute(() -> super.executeRequest(request).rethrow());
        else super.executeRequest(request).rethrow();

        return null;
    }

    @Override
    public String toString() {
        return "VoidExecutor";
    }

}
