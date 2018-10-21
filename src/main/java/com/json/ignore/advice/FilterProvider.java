package com.json.ignore.advice;

import com.json.ignore.filter.BaseFilter;
import com.json.ignore.filter.FilterFactory;
import com.json.ignore.filter.SessionUtil;
import org.springframework.core.MethodParameter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The type Filter provider.
 */
@Component
public class FilterProvider {
    private Map<Annotation, BaseFilter> items;

    /**
     * Instantiates a new Filter provider.
     */
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
                }
            }
        }
        return null;
    }

    /**
     * Is accept boolean.
     *
     * @param methodParameter the method parameter
     * @return the boolean
     */
    public boolean isAccept(MethodParameter methodParameter) {
        return FilterFactory.isAccept(methodParameter);
    }

    /**
     * Filter object.
     *
     * @param serverHttpRequest the server http request
     * @param methodParameter   the method parameter
     * @param object            the object
     * @return the object
     */
    public Object filter(ServerHttpRequest serverHttpRequest, MethodParameter methodParameter, Object object) {
        BaseFilter filter = getBaseFilter(serverHttpRequest, methodParameter);
        if (filter != null) {
            filter.filter(object, serverHttpRequest);
        }
        return object;
    }

    /**
     * Clear cache.
     */
    public void clearCache() {
        items.clear();
    }

    /**
     * Cache size int.
     *
     * @return the int
     */
    public int cacheSize() {
        return items.size();
    }
}
