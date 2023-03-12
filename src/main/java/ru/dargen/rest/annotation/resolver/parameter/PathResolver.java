package ru.dargen.rest.annotation.resolver.parameter;

import lombok.val;
import ru.dargen.rest.annotation.parameter.Path;
import ru.dargen.rest.annotation.resolver.AnnotationResolver;
import ru.dargen.rest.request.Request;

public class PathResolver implements AnnotationResolver<Path> {

    @Override
    public void resolve(Request request, Path annotation, Object object) {
        if (object != null || annotation.nullable()) {
            val path = object == null ? "null" : object.toString();
            if (annotation.value().isEmpty()) request.withPath(path);
            else request.setPath(request.getPath().replace("{" + annotation.value() + "}", path));
        }
    }

}
