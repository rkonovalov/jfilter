package com.json.ignore.advice;

import com.json.ignore.filter.BaseFilter;
import com.json.ignore.filter.FilterFactory;
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

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        /*
         * Attempt to find annotations in method and associated filter
         */
        return FilterFactory.isAccept(methodParameter);
    }

    @Override
    public Serializable beforeBodyWrite(Serializable obj, MethodParameter methodParameter, MediaType mediaType,
                                        Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest,
                                        ServerHttpResponse serverHttpResponse) {
        /*
         * Attempt to apply filter and filter/exclude fields from object
         */
        BaseFilter filter = FilterFactory.getFromFactory(serverHttpRequest, methodParameter);
        if (filter != null) {
            filter.filter(obj);
        }

        return obj;
    }
}
