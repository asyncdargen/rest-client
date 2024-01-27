package ru.dargen.rest.proxy.executor;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import ru.dargen.rest.proxy.Endpoint;
import ru.dargen.rest.request.Request;
import ru.dargen.rest.client.RestClient;
import ru.dargen.rest.response.Response;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.function.BiFunction;

@RequiredArgsConstructor
public abstract class AbstractExecutor implements BiFunction<Method, Object[], Object> {

    protected final Endpoint endpoint;
    protected final RestClient client;

    protected final Type responseType;

    public Object execute(Request request) {
        return executeRequest(request);
    }

    @SneakyThrows
    protected Response<?> executeRequest(Request request) {
        return client.execute(request, responseType);
    }

    @Override
    public Object apply(Method method, Object[] objects) {
        val request = endpoint.request().clone();

        for (int i = 0; i < endpoint.parameterResolvers().size(); i++) {
            val object = objects[i];
            endpoint.parameterResolvers()
                    .get(i)
                    .forEach(resolver -> resolver.resolve(request, object));
        }

        return execute(request);
    }

}
