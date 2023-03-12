package ru.dargen.rest.annotation.resolver;

import ru.dargen.rest.annotation.RequestParameter;
import ru.dargen.rest.annotation.RequestParameters;
import ru.dargen.rest.request.Request;

public class RequestParametersResolver implements AnnotationResolver<RequestParameters> {

    public static final RequestParameterResolver SINGLE_RESOLVER = new RequestParameterResolver();

    @Override
    public void resolve(Request request, RequestParameters annotation, Object object) {
        for (RequestParameter parameter : annotation.value()) {
            SINGLE_RESOLVER.resolve(request, parameter);
        }
    }

}
