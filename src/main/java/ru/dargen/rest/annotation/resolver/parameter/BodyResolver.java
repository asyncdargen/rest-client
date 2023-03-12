package ru.dargen.rest.annotation.resolver.parameter;

import ru.dargen.rest.annotation.parameter.Body;
import ru.dargen.rest.annotation.resolver.AnnotationResolver;
import ru.dargen.rest.request.Request;

public class BodyResolver implements AnnotationResolver<Body> {

    @Override
    public void resolve(Request request, Body annotation, Object object) {
        if (object != null || annotation.nullable())
            request.withBody(object);
    }

}
