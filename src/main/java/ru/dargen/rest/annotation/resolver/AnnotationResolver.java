package ru.dargen.rest.annotation.resolver;

import lombok.val;
import ru.dargen.rest.annotation.*;
import ru.dargen.rest.annotation.parameter.*;
import ru.dargen.rest.annotation.resolver.parameter.*;
import ru.dargen.rest.request.Request;
import ru.dargen.rest.util.Maps;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@FunctionalInterface
public interface AnnotationResolver<A extends Annotation> {

    Map<Class<? extends Annotation>, AnnotationResolver<? extends Annotation>> RESOLVER_MAP = Maps.buildHashMap(map -> {
        //Type & Method
        map.put(RequestMapping.class, new RequestMappingResolver());
        map.put(RequestPath.class, new RequestPathResolver());
        map.put(RequestMethod.class, new RequestMethodResolver());

        map.put(RequestOption.class, RequestOptionsResolver.SINGLE_RESOLVER);
        map.put(RequestOptions.class, new RequestOptionsResolver());

        map.put(RequestParameter.class, RequestParametersResolver.SINGLE_RESOLVER);
        map.put(RequestHeaders.class, new RequestHeadersResolver());

        map.put(RequestHeader.class, RequestHeadersResolver.SINGLE_RESOLVER);
        map.put(RequestParameters.class, new RequestParametersResolver());

        //Parameter
        map.put(Body.class, new BodyResolver());
        map.put(Path.class, new PathResolver());
        map.put(Header.class, new HeaderResolver());
        map.put(Parameter.class, new ParameterResolver());
        map.put(Method.class, new MethodResolver());
        map.put(Authorization.class, new AuthorizationResolver());
    });

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
