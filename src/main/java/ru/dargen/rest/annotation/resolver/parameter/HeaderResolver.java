package ru.dargen.rest.annotation.resolver.parameter;

import ru.dargen.rest.annotation.parameter.Header;
import ru.dargen.rest.annotation.resolver.AnnotationResolver;
import ru.dargen.rest.request.Request;

public class HeaderResolver implements AnnotationResolver<Header> {

    @Override
    public void resolve(Request request, Header annotation, Object object) {
        if (object != null || annotation.nullable())
            request.withHeader(annotation.value(), object == null ? "null" : object.toString());
    }

}
