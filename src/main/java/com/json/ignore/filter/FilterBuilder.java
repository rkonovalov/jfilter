package com.json.ignore.filter;

import org.springframework.core.MethodParameter;
import org.springframework.http.server.ServletServerHttpRequest;

/**
 * Interface used in FilterFactory for configuring list of filters
 */

@FunctionalInterface
public interface FilterBuilder {
    /**
     *
     * @param request {@link ServletServerHttpRequest} http request
     * @param methodParameter {@link MethodParameter} method parameter
     * @return object instance of inherited class from {@link BaseFilter}
     */
    BaseFilter build(ServletServerHttpRequest request, MethodParameter methodParameter);
}
