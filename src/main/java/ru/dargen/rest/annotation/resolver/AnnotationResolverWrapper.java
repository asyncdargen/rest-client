package ru.dargen.rest.annotation.resolver;

import lombok.RequiredArgsConstructor;
import ru.dargen.rest.request.Request;

import java.lang.annotation.Annotation;

@SuppressWarnings("all")
@RequiredArgsConstructor
public class AnnotationResolverWrapper {

    private final Annotation annotation;
    private final AnnotationResolver delegateResolver;

    public void resolve(Request request, Object object) {
        delegateResolver.resolve(request, annotation, object);
    }

}
