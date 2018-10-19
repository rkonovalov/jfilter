package com.json.ignore.filter;

import org.springframework.core.MethodParameter;
import org.springframework.http.server.ServletServerHttpRequest;

@FunctionalInterface
public interface FilterBuilder {
    BaseFilter build(ServletServerHttpRequest request, MethodParameter methodParameter);
}
