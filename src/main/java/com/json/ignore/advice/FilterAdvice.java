package com.json.ignore.advice;

import com.json.ignore.filter.BaseFilter;
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
 * Class which handle all responses from web service and tries to filter it
 * <p>
 * This class will be detected and instantiated automatically by Spring Framework
 * The main task of this class is checking if response method has filter annotation and try to apply filters
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

        BaseFilter filter = filterProvider.getFilter(methodParameter);
        if (filter != null) {
            return new FilterClassWrapper(obj, filter.getIgnoreList(obj, serverHttpRequest));
        } else
            return obj;
    }
}
