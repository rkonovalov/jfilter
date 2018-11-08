package com.json.ignore.filter;

import com.json.ignore.request.RequestMethodParameter;
import com.json.ignore.request.RequestSession;
import org.springframework.core.MethodParameter;

/**
 * This class is base class of filtration mechanism
 */
public abstract class BaseFilter {
    private final RequestMethodParameter requestMethodParameter;

    /**
     * Creates a new instance of the {@link BaseFilter} class.
     *
     * @param methodParameter {@link MethodParameter}
     */
    protected BaseFilter(MethodParameter methodParameter) {
        this.requestMethodParameter = new RequestMethodParameter(methodParameter);
    }

    /**
     * Returns RequestMethodParameter
     * @return {@link RequestMethodParameter}
     */
    protected RequestMethodParameter getRequestMethodParameter() {
        return requestMethodParameter;
    }

    protected abstract void setConfig(MethodParameter methodParameter);

    /**
     * Returns list of ignorable fields of object
     * @param object {@link Object}
     * @param request {@link RequestSession}
     * @return {@link FilterFields}
     */
    public abstract FilterFields getFields(Object object, RequestSession request);
}
