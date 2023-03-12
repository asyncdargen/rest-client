package ru.dargen.rest.annotation.resolver;

import ru.dargen.rest.annotation.RequestParameter;
import ru.dargen.rest.request.Request;

public class RequestParameterResolver implements AnnotationResolver<RequestParameter> {

    @Override
    public void resolve(Request request, RequestParameter annotation, Object object) {
        request.withParameter(annotation.key(), annotation.value());
    }

}
