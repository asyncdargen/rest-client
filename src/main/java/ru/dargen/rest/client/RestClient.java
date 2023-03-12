package ru.dargen.rest.client;

import ru.dargen.rest.request.Request;
import ru.dargen.rest.serializer.BodyAdapter;
import ru.dargen.rest.response.Response;

public interface RestClient {

    BodyAdapter getBodyAdapter();

    void setBodyAdapter(BodyAdapter adapter);

    Request getBaseRequest();

    <T> Response<T> execute(Request request, Class<T> responseType);

    <I> I createController(Class<I> type);

}
