package ru.dargen.rest.proxy.executor;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.val;
import ru.dargen.rest.client.RestClient;
import ru.dargen.rest.proxy.Endpoint;
import ru.dargen.rest.request.Request;
import ru.dargen.rest.util.Json;

import java.lang.reflect.Type;

public class JsonQueryResponseExecutor extends ResponseBodyExecutor {

    protected final String query;
    protected final Type exctractedType;

    public JsonQueryResponseExecutor(Endpoint endpoint, RestClient client, String query, Type responseType) {
        super(endpoint, client, JsonObject.class);
        exctractedType = responseType;
        this.query = query;
    }

    @Override
    public Object execute(Request request) {
        val json = Json.query((JsonObject) super.execute(request), query);

        if (json == null)
            return null;
        else if (exctractedType == JsonElement.class)
            return json;
        else if (exctractedType == JsonObject.class)
            return json.getAsJsonObject();
        else if (exctractedType == JsonArray.class)
            return json.getAsJsonArray();

        val bodyAdapter = client.getBodyAdapter();
        val serialized = bodyAdapter.serialize(json);

        return bodyAdapter.deserialize(serialized, exctractedType);
    }

}
