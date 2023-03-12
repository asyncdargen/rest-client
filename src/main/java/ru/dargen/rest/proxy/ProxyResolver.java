package ru.dargen.rest.proxy;

import lombok.experimental.UtilityClass;
import lombok.val;
import ru.dargen.rest.proxy.executor.*;
import ru.dargen.rest.annotation.resolver.AnnotationResolver;
import ru.dargen.rest.request.Request;
import ru.dargen.rest.client.RestClient;
import ru.dargen.rest.response.Response;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@UtilityClass
public class ProxyResolver {

    public <I> I createProxy(Class<I> interfaceClass, RestClient client) {
        val baseRequest = resolveRequest(client.getBaseRequest(), interfaceClass);

        val invocationHandler = new BindingInvocationHandler();
        val proxy = Proxy.newProxyInstance(
                interfaceClass.getClassLoader(),
                new Class[]{interfaceClass},
                invocationHandler
        );

        for (Method method : interfaceClass.getDeclaredMethods()) {
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
            return String.format("RestProxy[%s:%s]", interfaceClass, client);
        });
        invocationHandler.bind(BindingInvocationHandler.METHOD_EQUALS, (method, args) -> {
            return args[0] == proxy;
        });
        invocationHandler.bind(BindingInvocationHandler.METHOD_HASH_CODE, (method, args) -> {
            return invocationHandler.hashCode();
        });

        return (I) proxy;
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
