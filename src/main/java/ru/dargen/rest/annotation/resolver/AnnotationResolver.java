package ru.dargen.rest.annotation.resolver;

import lombok.val;
import ru.dargen.rest.annotation.*;
import ru.dargen.rest.annotation.parameter.*;
import ru.dargen.rest.annotation.resolver.parameter.*;
import ru.dargen.rest.request.Request;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Map.entry;

@FunctionalInterface
public interface AnnotationResolver<A extends Annotation> {

    Map<Class<? extends Annotation>, AnnotationResolver<? extends Annotation>> RESOLVER_MAP = Map.ofEntries(
        //Type & Method
        entry(RequestMapping.class, new RequestMappingResolver()),
        entry(RequestPath.class, new RequestPathResolver()),
        entry(RequestMethod.class, new RequestMethodResolver()),

        entry(RequestOption.class, RequestOptionsResolver.SINGLE_RESOLVER),
        entry(RequestOptions.class, new RequestOptionsResolver()),

        entry(RequestParameter.class, RequestParametersResolver.SINGLE_RESOLVER),
        entry(RequestHeaders.class, new RequestHeadersResolver()),

        entry(RequestHeader.class, RequestHeadersResolver.SINGLE_RESOLVER),
        entry(RequestParameters.class, new RequestParametersResolver()),

        //Parameter
        entry(Body.class, new BodyResolver()),
        entry(Path.class, new PathResolver()),
        entry(Header.class, new HeaderResolver()),
        entry(Parameter.class, new ParameterResolver()),
        entry(Method.class, new MethodResolver()),
        entry(Authorization.class, new AuthorizationResolver())
    );

    @SuppressWarnings("rawtypes")
    static AnnotationResolver getFor(Class<? extends Annotation> annotationClass) {
        return RESOLVER_MAP.get(annotationClass);
    }

    @SuppressWarnings("rawtypes")
    static AnnotationResolver getFor(Annotation annotation) {
        return getFor(annotation.annotationType());
    }

    static AnnotationResolverWrapper getWrapperFor(Annotation annotation) {
        val resolver = getFor(annotation.annotationType());

        return resolver == null ? null : new AnnotationResolverWrapper(annotation, resolver);
    }

    static List<AnnotationResolverWrapper> getWrappersFor(Annotation[] annotations) {
        return Arrays.stream(annotations)
                .map(AnnotationResolver::getWrapperFor)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    void resolve(Request request, A annotation, Object object);

    default void resolve(Request request, A annotation) {
        resolve(request, annotation, null);
    }

}
