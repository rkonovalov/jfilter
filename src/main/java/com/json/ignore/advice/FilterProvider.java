package com.json.ignore.advice;

import com.json.ignore.filter.BaseFilter;
import com.json.ignore.filter.FilterFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class FilterProvider {
    private Map<Annotation, BaseFilter> items;

    public FilterProvider() {
        this.items = new ConcurrentHashMap<>();
    }

    private BaseFilter getBaseFilter(ServerHttpRequest serverHttpRequest, MethodParameter methodParameter) {
        //String key = methodParameter.getMethod().toString();
        Annotation key = FilterFactory.getFilterAnnotation(methodParameter);

        if (key != null) {
            if (items.containsKey(key)) {
                /*
                 * Retrieve filter from cache
                 */
                return items.get(key);
            } else {
                /*
                 * Create and put filter in cache
                 */
                BaseFilter filter = FilterFactory.getFromFactory(serverHttpRequest, methodParameter);
                if (filter != null) {
                    items.put(key, filter);
                    return filter;
                } else
                    return null;
            }
        } else
            return null;
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

    public void clearCache() {
        items.clear();
    }

    public int cacheSize() {
        return items.size();
    }
}
