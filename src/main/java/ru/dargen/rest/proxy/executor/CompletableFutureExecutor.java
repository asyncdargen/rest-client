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
        return CompletableFuture.supplyAsync(() -> executor.execute(request));
    }

    @Override
    public String toString() {
        return "CompletableFutureExecutor[" + executor + "]";
    }
}
