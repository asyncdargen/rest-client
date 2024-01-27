package ru.dargen.rest.annotation.resolver.parameter;

import ru.dargen.rest.annotation.parameter.Parameter;
import ru.dargen.rest.annotation.resolver.AnnotationResolver;
import ru.dargen.rest.request.Request;

public class ParameterResolver implements AnnotationResolver<Parameter> {

    @Override
    public void resolve(Request request, Parameter annotation, Object object) {
        if (object != null || annotation.nullable()) {
            request.withParameter(annotation.value(), object == null ? "null" : object.toString());
        }
    }

}
