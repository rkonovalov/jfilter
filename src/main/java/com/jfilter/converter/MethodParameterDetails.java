package com.jfilter.converter;

import com.jfilter.filter.FilterFields;
import org.springframework.http.MediaType;
import java.io.Serializable;
import java.util.Objects;

/**
 * This class contains details retrieved from {@link com.jfilter.components.FilterAdvice#beforeBodyWrite}
 */
public class MethodParameterDetails implements Serializable {
    private static final long serialVersionUID = 2481023447068160651L;

    private int methodHashCode;
    private MediaType mediaType;
    private FilterFields filterFields;

    /**
     * Constructor
     *
     * @param methodHashCode hash code of method in Spring controller
     * @param mediaType      {@link MediaType} media type of http message
     * @param filterFields   {@link FilterFields} fields whish should be kept or hidden
     */
    public MethodParameterDetails(int methodHashCode, MediaType mediaType, FilterFields filterFields) {
        this.methodHashCode = methodHashCode;
        this.mediaType = mediaType;
        this.filterFields = filterFields;
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
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof MethodParameterDetails)) return false;
        MethodParameterDetails that = (MethodParameterDetails) object;
        return Objects.equals(methodHashCode, that.methodHashCode) &&
                Objects.equals(mediaType, that.mediaType) &&
                Objects.equals(filterFields, that.filterFields);
    }

    @Override
    public int hashCode() {
        return Objects.hash(methodHashCode, mediaType, filterFields);
    }
}
