package ru.dargen.rest.util;

import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@UtilityClass
public class Maps {

    public <K, V> Map<K, V> buildMap(Map<K, V> map, Consumer<Map<K, V>> builder) {
        builder.accept(map);
        return map;
    }

    public <K, V> Map<K, V> buildHashMap(Consumer<Map<K, V>> builder) {
        return buildMap(new HashMap<>(), builder);
    }

}
