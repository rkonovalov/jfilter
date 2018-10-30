package com.json.ignore.filter;

import com.json.ignore.filter.field.FieldProcessor;
import com.json.ignore.request.RequestMethodParameter;
import org.springframework.core.MethodParameter;
import org.springframework.http.server.ServerHttpRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class is base class strategy filtration
 */
public abstract class BaseFilter {
    private final RequestMethodParameter requestMethodParameter;

    protected BaseFilter(MethodParameter methodParameter) {
        this.requestMethodParameter = new RequestMethodParameter(methodParameter);
    }

    protected <K, V> void appendToMap(Map<K, List<V>> map, K key, List<V> value) {
        map.computeIfAbsent(key, k -> new ArrayList<>()).addAll(value);
    }

    protected <K, V> void appendToMap(Map<K, List<V>> map, Map<K, List<V>> mapValues) {
        mapValues.forEach((k, v) -> appendToMap(map, k, v));
    }

    protected RequestMethodParameter getRequestMethodParameter() {
        return requestMethodParameter;
    }

    protected abstract void setConfig(MethodParameter methodParameter);

    public abstract void filter(Object object, ServerHttpRequest request);

    protected void filter(Object object, FieldProcessor filterProcessor) {
        filterProcessor.filter(object);
    }

    public abstract Map<Class, List<String>> getIgnoreList(Object object, ServerHttpRequest request);
}
