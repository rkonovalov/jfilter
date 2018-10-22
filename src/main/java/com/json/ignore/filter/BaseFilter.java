package com.json.ignore.filter;

import com.json.ignore.FieldAccessException;
import com.json.ignore.request.RequestMethodParameter;
import org.springframework.core.MethodParameter;
import org.springframework.http.server.ServerHttpRequest;

/**
 * This class is base class strategy filtration
 */
public abstract class BaseFilter {
    private RequestMethodParameter requestMethodParameter;

    public BaseFilter(MethodParameter methodParameter) {
        this.requestMethodParameter = new RequestMethodParameter(methodParameter);
    }

    protected RequestMethodParameter getRequestMethodParameter() {
        return requestMethodParameter;
    }

    protected abstract void setConfig(MethodParameter methodParameter);

    public abstract void filter(Object object, ServerHttpRequest request) throws FieldAccessException;
}
