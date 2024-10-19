package ru.dargen.rest.proxy.executor;

import ru.dargen.rest.request.Request;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static java.util.concurrent.CompletableFuture.supplyAsync;

public class AsyncExecutor extends AbstractExecutor {

    public static final Executor EXECUTOR = Executors.newFixedThreadPool(3);

    private final AbstractExecutor executor;

    public AsyncExecutor(AbstractExecutor executor) {
        super(executor.endpoint, executor.client, executor.responseType);
        this.executor = executor;
    }

    @Override
    public Object execute(Request request) {
        return supplyAsync(() -> executor.execute(request), EXECUTOR);
    }

    @Override
    public String toString() {
        return "CompletableFutureExecutor[" + executor + "]";
    }
}
