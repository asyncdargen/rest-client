package ru.dargen.rest.request;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public record RequestOption<T>(String name, T value) {

    //Options
    public static final RequestOption<Integer> REQUEST_TIMEOUT = new RequestOption<>("request_timeout", 30_000);
    public static final RequestOption<Integer> READ_TIMEOUT = new RequestOption<>("read_timeout", 30_000);
    public static final RequestOption<Boolean> USE_CACHE = new RequestOption<>("use_cache", true);

    //Utility
    private static final Map<String, RequestOption<?>> VALUES = Arrays.stream(RequestOption.class.getDeclaredFields())
            .filter(field -> field.getType() == RequestOption.class)
            .filter(field -> Modifier.isStatic(field.getModifiers()))
            .map(field -> {
                try {
                    return Map.entry(field.getName(), (RequestOption<?>) field.get(null));
                } catch (IllegalAccessException e) {
                    return null;
                }
            })
            .filter(Objects::nonNull)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    public static Collection<RequestOption<?>> values() {
        return VALUES.values();
    }

    public static RequestOption<?> valueOf(String name) {
        return VALUES.get(name);
    }

}
