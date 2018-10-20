package com.json.ignore.advice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.io.Serializable;

/**
 * Spring controller advice
 */
@ControllerAdvice
public class FilterAdvice implements ResponseBodyAdvice<Serializable> {
    private FilterProvider filterProvider;

    @Autowired
    public void setFilterProvider(FilterProvider filterProvider) {
        this.filterProvider = filterProvider;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        /*
         * Attempt to find annotations in method and associated filter
         */
        return filterProvider.isAccept(methodParameter);
    }

    @Override
    public Serializable beforeBodyWrite(Serializable obj, MethodParameter methodParameter, MediaType mediaType,
                                        Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest,
                                        ServerHttpResponse serverHttpResponse) {

        return (Serializable) filterProvider.filter(serverHttpRequest, methodParameter, obj);
    }
}
