package ru.dargen.rest.proxy;

import lombok.SneakyThrows;
import lombok.val;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class BindingInvocationHandler implements InvocationHandler {

    public static final Method METHOD_TO_STRING = getRootMethod("toString");
    public static final Method METHOD_HASH_CODE = getRootMethod("hashCode");
    public static final Method METHOD_EQUALS = getRootMethod("equals", Object.class);

    private final Map<Method, BiFunction<Method, Object[], Object>> bindings = new HashMap<>();

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        val executor = bindings.get(method);
        if (executor == null)
            throw new IllegalAccessException("Not bounded method called " + method);

        return executor.apply(method, args);
    }

    public BindingInvocationHandler bind(Method method, BiFunction<Method, Object[], Object> executor) {
        bindings.put(method, executor);
        return this;
    }

    public BindingInvocationHandler bind(Method method, BiConsumer<Method, Object[]> executor) {
        return bind(method, (method0, args) -> {
            executor.accept(method0, args);
            return null;
        });
    }

    @SneakyThrows
    private static Method getRootMethod(String name, Class<?>... parametersTypes) {
        return Object.class.getDeclaredMethod(name, parametersTypes);
    }

}
