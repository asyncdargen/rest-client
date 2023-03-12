package ru.dargen.rest.annotation.resolver;

import ru.dargen.rest.annotation.RequestHeader;
import ru.dargen.rest.request.Request;

public class RequestHeaderResolver implements AnnotationResolver<RequestHeader> {

    @Override
    public void resolve(Request request, RequestHeader annotation, Object object) {
        request.withHeader(annotation.key(), annotation.value());
    }

}
