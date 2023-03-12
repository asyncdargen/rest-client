package ru.dargen.rest.annotation.resolver;

import ru.dargen.rest.annotation.RequestMapping;
import ru.dargen.rest.request.Request;

public class RequestMappingResolver implements AnnotationResolver<RequestMapping> {

    @Override
    public void resolve(Request request, RequestMapping annotation, Object object) {
        request.withPath(annotation.value());
        request.withMethod(annotation.method());
    }

}
