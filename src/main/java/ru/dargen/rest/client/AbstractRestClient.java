package ru.dargen.rest.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.dargen.rest.proxy.ProxyResolver;
import ru.dargen.rest.request.Request;
import ru.dargen.rest.response.Response;
import ru.dargen.rest.serializer.BodyAdapter;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.function.Consumer;

@Getter @Setter
@AllArgsConstructor
public abstract class AbstractRestClient implements RestClient {

    protected BodyAdapter bodyAdapter;

    protected final Request request = new Request();

    @Override
    public <T> Response<T> execute(Request request, Type responseType) {
        var response = execute(request);

        return TransformBodyStrategy.transform(response, responseType, bodyAdapter);
    }

    @Override
    public RestClient updateRequest(Consumer<Request> updater) {
        updater.accept(request);
        return this;
    }

    @Override
    public <I> I createController(Class<I> type, Consumer<Request> request) {
        var proxyRequest = this.request.clone();
        if (request != null) {
            request.accept(proxyRequest);
        }

        return ProxyResolver.createProxy(type, this, proxyRequest);
    }

    @Override
    public <I> I createController(Class<I> type) {
        return createController(type, null);
    }

    abstract Response<InputStream> execute(Request request);

}
