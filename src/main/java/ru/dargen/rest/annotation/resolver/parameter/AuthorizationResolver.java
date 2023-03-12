package ru.dargen.rest.annotation.resolver.parameter;

import ru.dargen.rest.annotation.parameter.Authorization;
import ru.dargen.rest.annotation.resolver.AnnotationResolver;
import ru.dargen.rest.request.Request;

public class AuthorizationResolver implements AnnotationResolver<Authorization> {

    @Override
    public void resolve(Request request, Authorization annotation, Object object) {
        if (object != null || annotation.nullable())
            request.withHeader("Authorization", annotation.value() + " " + object);
    }

}
