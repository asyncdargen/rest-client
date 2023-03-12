package ru.dargen.rest.annotation.resolver;

import ru.dargen.rest.annotation.RequestPath;
import ru.dargen.rest.request.Request;

public class RequestPathResolver implements AnnotationResolver<RequestPath> {

    @Override
    public void resolve(Request request, RequestPath annotation, Object object) {
        request.withPath(annotation.value());
    }

}
