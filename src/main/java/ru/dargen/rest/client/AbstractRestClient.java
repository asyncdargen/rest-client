package ru.dargen.rest.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import ru.dargen.rest.proxy.ProxyResolver;
import ru.dargen.rest.request.Request;
import ru.dargen.rest.serializer.BodyAdapter;
import ru.dargen.rest.response.Response;

import java.io.InputStream;

@Getter @Setter
@AllArgsConstructor
public abstract class AbstractRestClient implements RestClient {

    protected BodyAdapter bodyAdapter;

    protected final Request baseRequest = new Request();

    @Override
    public <T> Response<T> execute(Request request, Class<T> responseType) {
        val response = execute(request);

        return TransformBodyStrategy.transform(response, responseType, bodyAdapter);
    }

    @Override
    public <I> I createController(Class<I> type) {
        return ProxyResolver.createProxy(type, this);
    }

    abstract Response<InputStream> execute(Request request);

}
