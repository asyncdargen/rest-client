package ru.dargen.rest.proxy.executor;

import ru.dargen.rest.request.Request;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureExecutor extends AbstractExecutor {

    private final AbstractExecutor executor;

    public CompletableFutureExecutor(AbstractExecutor executor) {
        super(executor.endpoint, executor.client, executor.responseType);
        this.executor = executor;
    }

    @Override
    public Object execute(Request request) {
        return responseType == Void.TYPE || responseType == Void.class
                ? CompletableFuture.runAsync(() -> executor.execute(request))
                : CompletableFuture.supplyAsync(() -> executor.execute(request));
    }

}
