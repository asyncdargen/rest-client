package ru.dargen.rest.annotation.resolver.parameter;

import ru.dargen.rest.annotation.parameter.Method;
import ru.dargen.rest.annotation.resolver.AnnotationResolver;
import ru.dargen.rest.request.HttpMethod;
import ru.dargen.rest.request.Request;

public class MethodResolver implements AnnotationResolver<Method> {

    @Override
    public void resolve(Request request, Method annotation, Object object) {
        if (object != null)
            request.withMethod((HttpMethod) object);
    }

}
