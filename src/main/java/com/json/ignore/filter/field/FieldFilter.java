package com.json.ignore.filter.field;

import com.json.ignore.FieldAccessException;
import com.json.ignore.filter.BaseFilter;
import org.springframework.core.MethodParameter;
import org.springframework.http.server.ServerHttpRequest;

/**
 * This class used for simple filtration of object's fields based on FieldFilterSetting configuration
 */
public class FieldFilter extends BaseFilter {

    /**
     * Array of {@link FieldFilterSetting} configuration annotations
     */
    private FieldFilterSetting[] config;

    /**
     * Constructor
     * @param methodParameter {@link MethodParameter} Rest method of Rest controller
     */
    public FieldFilter(MethodParameter methodParameter) {
        super(methodParameter);
        setConfig(methodParameter);
    }

    /**
     * Attempt to retrieve all FieldFilterSetting annotations from method
     * @param methodParameter {@link MethodParameter} method parameter
     */
    @Override
    protected void setConfig(MethodParameter methodParameter) {
        config = getRequestMethodParameter().getSettingAnnotations();
    }

    /**
     * Attempt to filter object fields if filter annotations is configured
     * @param object {@link Object} object which fields will be filtered
     * @param request {@link ServerHttpRequest} http request
     * @throws FieldAccessException exception throws on {@link IllegalAccessException}
     */
    @Override
    public void filter(Object object, ServerHttpRequest request) throws FieldAccessException {
        if(object != null) {
            filter(object, new FieldFilterProcessor(config));
        }
    }
}
