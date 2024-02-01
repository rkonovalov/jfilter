package com.jfilter.components;

import com.jfilter.converter.MethodParameterDetails;
import com.jfilter.converter.FilterClassWrapper;
import com.jfilter.filter.FilterFields;
import com.jfilter.request.RequestSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import jakarta.servlet.http.HttpServletRequest;
import java.io.Serializable;

import static com.jfilter.filter.FilterFields.EMPTY_FIELDS;

/**
 * Class which handle all responses from web service and tries to filter it
 *
 * <p>This class will be detected and instantiated automatically by Spring Framework
 * The main task of this class is checking if response method has filter annotation and try to apply filters
 */

@ControllerAdvice
public final class FilterAdvice implements ResponseBodyAdvice<Object> {
    private FilterProvider filterProvider;
    private DynamicFilterProvider dynamicFilterProvider;
    private FilterConfiguration filterConfiguration;

    @Autowired
    public void setFilterProvider(FilterProvider filterProvider) {
        this.filterProvider = filterProvider;
    }

    @Autowired
    public FilterAdvice setDynamicFilterProvider(DynamicFilterProvider dynamicFilterProvider) {
        this.dynamicFilterProvider = dynamicFilterProvider;
        return this;
    }

    @Autowired
    public FilterAdvice setFilterConfiguration(FilterConfiguration filterConfiguration) {
        this.filterConfiguration = filterConfiguration;
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
        return filterConfiguration.isEnabled() &&
                (filterProvider.isAccept(methodParameter) || DynamicFilterProvider.isAccept(methodParameter));
    }

    /**
     * Attempt to find filter and extract ignorable fields from methodParameter
     *
     * @param obj                {@link Object} object sent from response of Spring Web Service
     * @param methodParameter    {@link MethodParameter}
     * @param mediaType          {@link MediaType}
     * @param aClass             {@link HttpMessageConverter}
     * @param serverHttpRequest  {@link ServerHttpRequest}
     * @param serverHttpResponse {@link ServerHttpResponse}
     * @return {@link FilterClassWrapper} if BaseFilter is found FilterClassWrapper contains list of ignorable fields,
     * else returns FilterClassWrapper with HashMap zero length
     */
    @Override
    public Serializable beforeBodyWrite(Object obj, MethodParameter methodParameter, MediaType mediaType,
                                        Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest,
                                        ServerHttpResponse serverHttpResponse) {

        FilterFields filterFields = EMPTY_FIELDS.get();

        //Getting HttpServletRequest from serverHttpRequest
        HttpServletRequest servletServerHttpRequest = ((ServletServerHttpRequest) serverHttpRequest).getServletRequest();
        RequestSession requestSession = new RequestSession(servletServerHttpRequest);

        //Retrieve filterable fields from static filters
        filterProvider.getOptionalFilter(methodParameter)
                .ifPresent(filter -> filterFields.appendToMap(filter.getFields(obj, requestSession)));

        //Retrieve filterable fields from dynamic filters
        filterFields.appendToMap(dynamicFilterProvider.getFields(methodParameter, requestSession));

        MethodParameterDetails methodParameterDetails = new MethodParameterDetails(methodParameter, mediaType, filterFields);

        return new FilterClassWrapper(obj, methodParameterDetails);
    }
}
