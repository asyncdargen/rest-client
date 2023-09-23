package ru.dargen.rest.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import lombok.experimental.UtilityClass;
import lombok.val;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class Json {

    public Pattern INT_PATTERN = Pattern.compile("^\\d+$");

    public Pattern QUERY_PATTERN = Pattern.compile("([\\w-]+)|(\\[(\\d+)\\])");

    public JsonElement query(JsonElement element, String query) {
        if (element == null || query == null)
            return null;

        Matcher matcher = QUERY_PATTERN.matcher(query);

        while (element != null && matcher.find()) {
            String key = matcher.group(1);
            String indexes = matcher.group(3);

            if (key != null && element.isJsonArray() && key.startsWith("-")) {
                key = key.substring(1);
                element = flatter(element.getAsJsonArray());

                if (key.isEmpty())
                    continue;
            }
            if (key != null) {
                element = mapElements(element, key);
            } else if (indexes != null && element.isJsonArray()) {
                element = sliceIndexes(element.getAsJsonArray(), indexes);
            } else element = null;
        }

        return element;
    }

    public JsonArray flatter(JsonArray array) {
        val newArray = new JsonArray();

        array.forEach(element -> {
            if (element.isJsonArray()) element.getAsJsonArray().forEach(newArray::add);
            else newArray.add(element);
        });

        return newArray;
    }

    public JsonElement mapElements(JsonElement element, String query) {
        if (element.isJsonObject())
            return element.getAsJsonObject().get(query);
        else if (element.isJsonArray()) {
            val array = new JsonArray();

            element.getAsJsonArray().forEach(arrayElement -> {
                if (arrayElement != null && arrayElement.isJsonObject())
                    array.add(arrayElement.getAsJsonObject().get(query));
            });

            return array.size() == 1 ? array.get(0) : array;
        }

        return null;
    }

    public JsonElement sliceIndexes(JsonArray array, String indexes) {
        Matcher matcher;

        if ((matcher = INT_PATTERN.matcher(indexes)).find())
            return array.get(Integer.parseInt(indexes));
        else return null;
    }

}
