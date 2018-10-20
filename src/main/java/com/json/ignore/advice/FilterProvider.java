package com.json.ignore.advice;

import com.json.ignore.filter.BaseFilter;
import com.json.ignore.filter.FilterFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class FilterProvider {
    private Map<String, BaseFilter> items;

    public FilterProvider() {
        this.items = new ConcurrentHashMap<>();
    }

    private BaseFilter getBaseFilter(ServerHttpRequest serverHttpRequest, MethodParameter methodParameter) {
        String key = methodParameter.getMethod().toString();

        if (items.containsKey(key)) {
            return items.get(key);
        } else {
            BaseFilter filter = FilterFactory.getFromFactory(serverHttpRequest, methodParameter);
            if (filter != null) {
                items.put(key, filter);
                return filter;
            } else
                return null;
        }
    }

    public void clearCache() {
        items.clear();
    }

    public boolean isAccept(MethodParameter methodParameter) {
        return FilterFactory.isAccept(methodParameter);
    }

    public Object filter(ServerHttpRequest serverHttpRequest, MethodParameter methodParameter, Object object) {
        BaseFilter filter = getBaseFilter(serverHttpRequest, methodParameter);
        if (filter != null) {
            filter.filter(object);
        }
        return object;
    }
}
