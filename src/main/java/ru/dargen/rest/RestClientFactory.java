package ru.dargen.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.experimental.UtilityClass;
import lombok.val;
import ru.dargen.rest.client.HttpBuiltinRestClient;
import ru.dargen.rest.client.RestClient;
import ru.dargen.rest.serializer.BodyAdapter;
import ru.dargen.rest.serializer.GsonBodyAdapter;

import java.util.function.Consumer;

@UtilityClass
public class RestClientFactory {

    public RestClient createHttpBuiltinClient() {
        return createHttpBuiltinClient(createGsonBodyAdapter());
    }

    public RestClient createHttpBuiltinClient(BodyAdapter bodyAdapter) {
        return new HttpBuiltinRestClient(bodyAdapter);
    }

    public BodyAdapter createGsonBodyAdapter(Consumer<GsonBuilder> builder) {
        val gsonBuilder = new GsonBuilder();

        if (builder != null)
            builder.accept(gsonBuilder);
        var gson = gsonBuilder.create();
        return new GsonBodyAdapter(() -> gson);
    }

    public BodyAdapter createGsonBodyAdapter(Gson gson) {
        return new GsonBodyAdapter(() -> gson);
    }

    public BodyAdapter createGsonBodyAdapter() {
        return createGsonBodyAdapter((Consumer<GsonBuilder>) null);
    }

}
