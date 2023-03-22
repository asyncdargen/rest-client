package ru.dargen.rest.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.dargen.rest.util.Maps;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class RequestOption<T> {

    private final String name;
    private final T value;

    //Options
    public static final RequestOption<Integer> REQUEST_TIMEOUT = new RequestOption<>("request_timeout", 30_000);
    public static final RequestOption<Integer> READ_TIMEOUT = new RequestOption<>("read_timeout", 30_000);
    public static final RequestOption<Boolean> USE_CACHE = new RequestOption<>("use_cache", true);

    //Utility
    private static final Map<String, RequestOption<?>> VALUES = Maps.buildHashMap(map -> {
        Arrays.stream(RequestOption.class.getDeclaredFields())
                .filter(field -> field.getType() == RequestOption.class)
                .filter(field -> Modifier.isStatic(field.getModifiers()))
                .forEach(field -> {
                    try {
                        map.put(field.getName(), (RequestOption<?>) field.get(null));
                    } catch (IllegalAccessException e) {}
                });
    });

    public static Collection<RequestOption<?>> values() {
        return VALUES.values();
    }

    public static RequestOption<?> valueOf(String name) {
        return VALUES.get(name);
    }

}
