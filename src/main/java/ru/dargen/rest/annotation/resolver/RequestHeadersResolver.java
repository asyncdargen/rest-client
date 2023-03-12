package ru.dargen.rest.annotation.resolver;

import ru.dargen.rest.annotation.RequestHeader;
import ru.dargen.rest.annotation.RequestHeaders;
import ru.dargen.rest.request.Request;

public class RequestHeadersResolver implements AnnotationResolver<RequestHeaders> {

    public static final RequestHeaderResolver SINGLE_RESOLVER = new RequestHeaderResolver();

    @Override
    public void resolve(Request request, RequestHeaders annotation, Object object) {
        for (RequestHeader header : annotation.value()) {
            SINGLE_RESOLVER.resolve(request, header);
        }
    }

}
