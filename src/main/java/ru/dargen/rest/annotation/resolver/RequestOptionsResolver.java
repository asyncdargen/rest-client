package ru.dargen.rest.annotation.resolver;

import ru.dargen.rest.annotation.RequestOption;
import ru.dargen.rest.annotation.RequestOptions;
import ru.dargen.rest.request.Request;

public class RequestOptionsResolver implements AnnotationResolver<RequestOptions> {

    public static final RequestOptionResolver SINGLE_RESOLVER = new RequestOptionResolver();

    @Override
    public void resolve(Request request, RequestOptions annotation, Object object) {
        for (RequestOption header : annotation.value()) {
            SINGLE_RESOLVER.resolve(request, header);
        }
    }

}
