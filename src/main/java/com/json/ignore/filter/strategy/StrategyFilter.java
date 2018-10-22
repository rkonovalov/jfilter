package com.json.ignore.filter.strategy;

import com.json.ignore.FieldAccessException;
import com.json.ignore.util.AnnotationUtil;
import com.json.ignore.filter.BaseFilter;
import com.json.ignore.util.SessionUtil;
import com.json.ignore.filter.field.FieldFilterProcessor;
import org.springframework.core.MethodParameter;
import org.springframework.http.server.ServerHttpRequest;

/**
 * This class used for strategy filtration of object's fields based on SessionStrategy configuration
 *
 */
public class StrategyFilter extends BaseFilter {
    /**
     * Array of {@link SessionStrategy} configuration annotations
     */
    private SessionStrategy[] config;

    /**
     * Constructor
     * @param methodParameter {@link MethodParameter} Rest method of Rest controller
     */
    public StrategyFilter(MethodParameter methodParameter) {
        super(methodParameter);
        setConfig(methodParameter);
    }

    /**
     * Attempt to retrieve all FieldFilterSetting annotations from method
     * @param methodParameter {@link MethodParameter} method parameter
     */
    @Override
    protected void setConfig(MethodParameter methodParameter) {
        config = AnnotationUtil.getStrategyAnnotations(methodParameter);
    }

    /**
     * Attempt to filter object fields if filter annotations is configured
     * @param object {@link Object} object which fields will be filtered
     * @throws FieldAccessException exception throws on {@link IllegalAccessException}
     */
    @Override
    public void filter(Object object, ServerHttpRequest request) throws FieldAccessException {
        SessionUtil sessionUtil = new SessionUtil(request);

        if (object != null && config != null) {
            for (SessionStrategy strategy : config) {
                if(sessionUtil.isSessionPropertyExists(sessionUtil.getSession(), strategy.attributeName(), strategy.attributeValue())) {
                    FieldFilterProcessor processor = new FieldFilterProcessor(strategy.ignoreFields());
                    processor.filterFields(object);
                }
            }
        }
    }
}
