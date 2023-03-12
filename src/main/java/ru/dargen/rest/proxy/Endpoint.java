package ru.dargen.rest.proxy;

import lombok.Data;
import ru.dargen.rest.annotation.resolver.AnnotationResolverWrapper;
import ru.dargen.rest.request.Request;

import java.util.List;

@Data
public class Endpoint {

    private final Request request;
    private final List<List<AnnotationResolverWrapper>> parameterResolvers;

}
