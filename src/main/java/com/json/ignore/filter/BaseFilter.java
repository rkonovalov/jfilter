package com.json.ignore.filter;

import com.json.ignore.request.RequestMethodParameter;
import org.springframework.core.MethodParameter;
import org.springframework.http.server.ServerHttpRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class is base class of filtration mechanism
 */
public abstract class BaseFilter {
    private final RequestMethodParameter requestMethodParameter;

    /**
     * Constructor
     * @param methodParameter {@link MethodParameter}
     */
    protected BaseFilter(MethodParameter methodParameter) {
        this.requestMethodParameter = new RequestMethodParameter(methodParameter);
    }

    /**
     * Append list items in map
     *
     * @param map {@link Map}
     * @param key key
     * @param value value
     * @param <K> generic key class
     * @param <V> generic value class
     */
    protected <K, V> void appendToMap(Map<K, List<V>> map, K key, List<V> value) {
        map.computeIfAbsent(key, k -> new ArrayList<>()).addAll(value);
    }

    /**
     * Append list items in map
     *
     * @param map {@link Map}
     * @param mapValues list of items
     * @param <K> generic key class
     * @param <V> generic value class
     */
    protected <K, V> void appendToMap(Map<K, List<V>> map, Map<K, List<V>> mapValues) {
        mapValues.forEach((k, v) -> appendToMap(map, k, v));
    }

    /**
     * Returns RequestMethodParameter
     * @return {@link RequestMethodParameter}
     */
    protected RequestMethodParameter getRequestMethodParameter() {
        return requestMethodParameter;
    }

    protected abstract void setConfig(MethodParameter methodParameter);

    /**
     * Returns list of ignorable fields of object
     * @param object {@link Object}
     * @param request {@link ServerHttpRequest}
     * @return {@link Map}
     */
    public abstract Map<Class, List<String>> getIgnoreList(Object object, ServerHttpRequest request);
}
