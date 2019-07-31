package com.jfilter.converter;

import com.jfilter.components.FilterAdvice;
import com.jfilter.components.FilterConverter;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;

import java.io.Serializable;

/**
 * Class contains Serializable object and list of fields which should be kept or hidden
 *
 * <p>Class used to send additional information from {@link FilterAdvice} to {@link FilterConverter}
 */
public class FilterClassWrapper implements Serializable {
    private static final long serialVersionUID = -6250969684778521840L;
    private final transient Object object;
    private MethodParameterDetails methodParameterDetails;

    /**
     * Creates a new instance of the {@link FilterClassWrapper} class.
     *
     * @param object                 Serializable object
     * @param methodParameterDetails objectMapper item
     */
    public FilterClassWrapper(Object object, MethodParameterDetails methodParameterDetails) {
        this.object = object;
        this.methodParameterDetails = methodParameterDetails;
    }

    /**
     * Serializable object retrieved from {@link FilterAdvice#beforeBodyWrite(Object, MethodParameter, MediaType, Class, ServerHttpRequest, ServerHttpResponse)}
     *
     * @return {@link Serializable}
     */
    public Object getObject() {
        return object;
    }

    /**
     * MethodParameter details retrieved from {@link FilterAdvice#beforeBodyWrite(Object, MethodParameter, MediaType, Class, ServerHttpRequest, ServerHttpResponse)}
     *
     * @return {@link MethodParameterDetails}
     */
    public MethodParameterDetails getMethodParameterDetails() {
        return methodParameterDetails;
    }
}
