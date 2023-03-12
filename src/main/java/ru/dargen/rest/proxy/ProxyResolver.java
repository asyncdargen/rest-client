package ru.dargen.rest.proxy;

import lombok.experimental.UtilityClass;
import lombok.val;
import ru.dargen.rest.annotation.resolver.AnnotationResolver;
import ru.dargen.rest.annotation.util.RecomputeController;
import ru.dargen.rest.client.RestClient;
import ru.dargen.rest.proxy.executor.*;
import ru.dargen.rest.request.Request;
import ru.dargen.rest.response.Response;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@UtilityClass
public class ProxyResolver {

    public <I> I createProxy(Class<I> interfaceClass, RestClient client) {
        val invocationHandler = new BindingInvocationHandler();
        val proxy = Proxy.newProxyInstance(
                interfaceClass.getClassLoader(),
                new Class[]{interfaceClass},
                invocationHandler
        );

        scanAndResolveMethods(interfaceClass, client, invocationHandler, proxy);

        return (I) proxy;
    }

    public void scanAndResolveMethods(
            Class<?> interfaceClass, RestClient client,
            BindingInvocationHandler invocationHandler, Object proxy) {
        val baseRequest = resolveRequest(client.getBaseRequest(), interfaceClass);

        for (Method method : interfaceClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(RecomputeController.class)) {
                invocationHandler.bind(method, (__, ___) -> {
                    invocationHandler.getBindings().clear();
                    scanAndResolveMethods(interfaceClass, client, invocationHandler, proxy);
                });
                continue;
            }

            val request = resolveRequest(baseRequest, method);
            val parameters = Arrays.stream(method.getParameterAnnotations())
                    .map(AnnotationResolver::getWrappersFor)
                    .collect(Collectors.toList());

            val executor = resolveExecutor(
                    method.getReturnType(), method.getGenericReturnType(),
                    new Endpoint(request, parameters), client
            );

            invocationHandler.bind(method, executor);
        }

        invocationHandler.bind(BindingInvocationHandler.METHOD_TO_STRING, (method, args) -> {
            return String.format("%s[%s]", interfaceClass.getName(), client);
        });
        invocationHandler.bind(BindingInvocationHandler.METHOD_EQUALS, (method, args) -> args[0] == proxy);
        invocationHandler.bind(BindingInvocationHandler.METHOD_HASH_CODE, (method, args) -> {
            return invocationHandler.hashCode();
        });
    }

    private AbstractExecutor resolveExecutor(Class<?> responseType, Type genericsResponseType, Endpoint endpoint, RestClient client) {
        if (responseType == Void.TYPE || responseType == Void.class)
            return new VoidExecutor(endpoint, client);
        else if (responseType == Response.class)
            return new ResponseExecutor(endpoint, client,
                    (Class<?>) ((ParameterizedType) genericsResponseType).getActualTypeArguments()[0]);
        else if (responseType == CompletableFuture.class) {
            val genericType = ((ParameterizedType) genericsResponseType).getActualTypeArguments()[0];
            return new CompletableFutureExecutor(resolveExecutor(
                    Class.class.isAssignableFrom(genericType.getClass())
                            ? (Class<?>) genericType
                            : (Class<?>) ((ParameterizedType) genericType).getRawType(),
                    genericType, endpoint, client
            ));
        } else return new ResponseBodyExecutor(endpoint, client, responseType);
    }

    @SuppressWarnings("all")
    private Request resolveRequest(Request request, AnnotatedElement element) {
        request = request.isEmpty() ? request : request.clone();

        for (val annotation : element.getAnnotations()) {
            AnnotationResolver.getFor(annotation).resolve(request, annotation);
        }

        return request;
    }

}
