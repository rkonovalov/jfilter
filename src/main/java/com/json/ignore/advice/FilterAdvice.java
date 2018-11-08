package com.json.ignore.advice;

import com.json.ignore.converter.FilterClassWrapper;
import com.json.ignore.filter.BaseFilter;
import com.json.ignore.request.RequestSession;
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
 *
 * <p>This class will be detected and instantiated automatically by Spring Framework
 * The main task of this class is checking if response method has filter annotation and try to apply filters
 */

@ControllerAdvice
public class FilterAdvice implements ResponseBodyAdvice<Serializable> {
    private FilterProvider filterProvider;
    private DynamicFilterProvider dynamicFilterProvider;

    @Autowired
    public void setFilterProvider(FilterProvider filterProvider) {
        this.filterProvider = filterProvider;
    }

    @Autowired
    public FilterAdvice setDynamicFilterProvider(DynamicFilterProvider dynamicFilterProvider) {
        this.dynamicFilterProvider = dynamicFilterProvider;
        return this;
    }

    /**
     * Attempt to find annotations in method and associated filter
     *
     * @param methodParameter {@link MethodParameter}
     * @param aClass          {@link HttpMessageConverter}
     * @return true if found, else false
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return filterProvider.isAccept(methodParameter) || DynamicFilterProvider.isAccept(methodParameter);
    }

    /**
     * Attempt to find filter and extract ignorable fields from methodParameter
     *
     * @param obj                {@link Serializable} object sent from response of Spring Web Service
     * @param methodParameter    {@link MethodParameter}
     * @param mediaType          {@link MediaType}
     * @param aClass             {@link HttpMessageConverter}
     * @param serverHttpRequest  {@link ServerHttpRequest}
     * @param serverHttpResponse {@link ServerHttpResponse}
     * @return {@link FilterClassWrapper} if BaseFilter is found FilterClassWrapper contains list of ignorable fields,
     * else returns FilterClassWrapper with HashMap zero length
     */
    @Override
    public Serializable beforeBodyWrite(Serializable obj, MethodParameter methodParameter, MediaType mediaType,
                                        Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest,
                                        ServerHttpResponse serverHttpResponse) {

        RequestSession requestSession = new RequestSession(serverHttpRequest);

        BaseFilter filter = filterProvider.getFilter(methodParameter);
        if (filter != null) {
            return new FilterClassWrapper(obj, filter.getFields(obj, requestSession));
        } else
            return new FilterClassWrapper(obj, dynamicFilterProvider.getFields(methodParameter, requestSession));
    }
}
