package ru.dargen.rest.serializer;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Type;

@Getter
@RequiredArgsConstructor
public class GsonBodyAdapter implements StringBodyAdapter {

    private final Gson gson;

    @Override
    public String serializeString(Object object) {
        return gson.toJson(object);
    }

    @Override
    public <T> T deserializeString(String rawString, Type type) {
        return gson.fromJson(rawString, type);
    }

}
