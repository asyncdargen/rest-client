package ru.dargen.rest.request;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.net.URLEncoder.encode;

@Getter
@Setter
public class Request {

    private String path = "";
    private HttpMethod method = HttpMethod.GET;

    private final Map<String, Object> options = new HashMap<>();

    private final Map<String, String> headers = new HashMap<>();
    private final Map<String, String> parameters = new HashMap<>();

    private Object body;

    public Request withPath(String path) {
        if (path.isEmpty())
            return this;

        this.path += (this.path.isEmpty() || this.path.endsWith("/") || path.startsWith("/") ? "" : "/") + path;
        return this;
    }

    public Request withMethod(HttpMethod method) {
        this.method = method;
        return this;
    }

    public <T> Request withOption(RequestOption<T> option, T value) {
        options.put(option.name(), value);
        return this;
    }

    public Request withOptions(Map<String, Object> options) {
        this.options.putAll(options);
        return this;
    }

    public <T> Request resetOption(RequestOption<T> option) {
        options.remove(option.name());
        return this;
    }

    public <T> boolean hasOption(RequestOption<T> option) {
        return options.containsKey(option.name());
    }

    @SuppressWarnings("unchecked")
    public <T> T getOption(RequestOption<T> option) {
        return (T) options.getOrDefault(option.name(), option.value());
    }

    public Request withHeader(String key, String value) {
        headers.put(key, value);
        return this;
    }

    public Request withParameter(String key, String value) {
        parameters.put(key, value);
        return this;
    }

    public Request withParameters(Map<String, String> parameters) {
        this.parameters.putAll(parameters);
        return this;
    }

    public Request withHeaders(Map<String, String> headers) {
        this.headers.putAll(headers);
        return this;
    }

    public Request withBody(Object body) {
        this.body = body;
        return this;
    }

    public String getCompletedPath() {
        return path + (parameters.isEmpty() ? "" : "?" + String.join("&", getEncodedParameters()));
    }

    public List<String> getEncodedParameters() {
        return parameters.entrySet().stream().map(parameter -> {
            try {
                return encode(parameter.getKey(), "UTF-8") + "=" + encode(parameter.getValue(), "UTF-8");
            } catch (Throwable throwable) {
                return parameter.getKey() + "=" + parameter.getValue();
            }
        }).collect(Collectors.toList());
    }

    public boolean hasBody() {
        return body != null;
    }

    public boolean isEmpty() {
        return path.isEmpty() && parameters.isEmpty() && headers.isEmpty() && body != null;
    }

    public Request clone() {
        return new Request()
                .withPath(path)
                .withMethod(method)
                .withOptions(options)
                .withParameters(parameters)
                .withHeaders(headers)
                .withBody(body);
    }

}
