package ru.dargen.rest.annotation.resolver;

import lombok.val;
import ru.dargen.rest.annotation.RequestOption;
import ru.dargen.rest.request.Request;

public class RequestOptionResolver implements AnnotationResolver<RequestOption> {

    @Override
    public void resolve(Request request, RequestOption annotation, Object object) {
        val option = (ru.dargen.rest.request.RequestOption<Object>) ru.dargen.rest.request.RequestOption.valueOf(annotation.option());

        request.withOption(option, parseOption(option, annotation.value()));
    }

    public Object parseOption(ru.dargen.rest.request.RequestOption<Object> option, String value) {
        val optionType = option.value().getClass();
        if (optionType == Integer.class) return Integer.parseInt(value);
        else if (optionType == Boolean.class) return Boolean.parseBoolean(value);
        else throw new IllegalStateException("Unresolvable option type");
    }

}
