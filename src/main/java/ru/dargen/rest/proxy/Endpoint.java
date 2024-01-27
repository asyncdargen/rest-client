package ru.dargen.rest.proxy;

import ru.dargen.rest.annotation.resolver.AnnotationResolverWrapper;
import ru.dargen.rest.request.Request;

import java.util.List;

public record Endpoint(Request request, List<List<AnnotationResolverWrapper>> parameterResolvers) {

}
