package ru.dargen.rest.serializer;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Type;
import java.util.function.Supplier;

@Getter
@RequiredArgsConstructor
public class GsonBodyAdapter implements StringBodyAdapter {

    private final Supplier<Gson> gson;

    @Override
    public String serializeString(Object object) {
        return gson.get().toJson(object);
    }

    @Override
    public <T> T deserializeString(String rawString, Type type) {
        return gson.get().fromJson(rawString, type);
    }

}
