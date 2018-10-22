package com.json.ignore.filter;

import com.json.ignore.FieldAccessException;
import org.springframework.core.MethodParameter;
import org.springframework.http.server.ServerHttpRequest;

/**
 * This class is base class strategy filtration
 */
public abstract class BaseFilter {

    public BaseFilter(MethodParameter methodParameter) {

    }

    protected abstract void setConfig(MethodParameter methodParameter);

    public abstract void filter(Object object, ServerHttpRequest request) throws FieldAccessException;
}
