package ru.dargen.rest.annotation.resolver;

import ru.dargen.rest.annotation.RequestMethod;
import ru.dargen.rest.request.Request;

public class RequestMethodResolver implements AnnotationResolver<RequestMethod> {

    @Override
    public void resolve(Request request, RequestMethod annotation, Object object) {
        request.withMethod(annotation.value());
    }

}
