package com.jfilter.converter;

import com.jfilter.filter.FilterFields;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * This class contains details retrieved from {@link com.jfilter.components.FilterAdvice#beforeBodyWrite}
 */
public class MethodParameterDetails implements Serializable {
    private static final long serialVersionUID = 2481023447068160651L;

    private MethodParameter methodParameter;
    private int methodHashCode;
    private MediaType mediaType;
    private FilterFields filterFields;

    /**
     * Constructor
     *
     * @param methodParameter       method of Spring controller
     * @param mediaType    {@link MediaType} media type of http message
     * @param filterFields {@link FilterFields} fields whish should be kept or hidden
     */
    public MethodParameterDetails(MethodParameter methodParameter, MediaType mediaType, FilterFields filterFields) {
        this.methodParameter = methodParameter;
        this.methodHashCode = methodParameter.getMethod().hashCode();
        this.mediaType = mediaType;
        this.filterFields = filterFields;
    }

    public MethodParameter getMethodParameter() {
        return methodParameter;
    }

    public int getMethodHashCode() {
        return methodHashCode;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public FilterFields getFilterFields() {
        return filterFields;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MethodParameterDetails)) return false;
        MethodParameterDetails that = (MethodParameterDetails) o;
        return methodHashCode == that.methodHashCode &&
                Objects.equals(methodParameter, that.methodParameter) &&
                Objects.equals(mediaType, that.mediaType) &&
                Objects.equals(filterFields, that.filterFields);
    }

    @Override
    public int hashCode() {
        return Objects.hash(methodParameter, methodHashCode, mediaType, filterFields);
    }
}
