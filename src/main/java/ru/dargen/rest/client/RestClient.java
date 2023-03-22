package ru.dargen.rest.client;

import ru.dargen.rest.request.Request;
import ru.dargen.rest.response.Response;
import ru.dargen.rest.serializer.BodyAdapter;

import java.lang.reflect.Type;

public interface RestClient {

    BodyAdapter getBodyAdapter();

    void setBodyAdapter(BodyAdapter adapter);

    Request getBaseRequest();

    <T> Response<T> execute(Request request, Type type);

    <I> I createController(Class<I> type);

}
