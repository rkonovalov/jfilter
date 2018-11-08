package com.json.ignore.filter.strategy;

import com.json.ignore.filter.BaseFilter;
import com.json.ignore.filter.FilterFields;
import com.json.ignore.filter.field.FieldFilterSetting;
import com.json.ignore.request.RequestSession;
import org.springframework.core.MethodParameter;

import java.util.*;

/**
 * This class used for strategy filtration of object's fields based on SessionStrategy configuration
 */
public class StrategyFilter extends BaseFilter {
    /**
     * Array of {@link SessionStrategy} configuration annotations
     */
    private SessionStrategy[] config;

    /**
     * Creates a new instance of the {@link StrategyFilter} class.
     *
     * @param methodParameter {@link MethodParameter} Rest method of Rest controller
     */
    public StrategyFilter(MethodParameter methodParameter) {
        super(methodParameter);
        setConfig(methodParameter);
    }

    /**
     * Attempt to retrieve all FieldFilterSetting annotations from method
     *
     * @param methodParameter {@link MethodParameter} method parameter
     */
    @Override
    protected void setConfig(MethodParameter methodParameter) {
        config = getRequestMethodParameter().getStrategyAnnotations(methodParameter);
    }

    @Override
    public FilterFields getFields(Object object, RequestSession request) {
        FilterFields result = new FilterFields();

        for (SessionStrategy strategy : config) {
            if (request.isSessionPropertyExists(strategy.attributeName(),
                    strategy.attributeValue())) {

                for (FieldFilterSetting setting : strategy.ignoreFields()) {
                    result.appendToMap(setting.className(), Arrays.asList(setting.fields()));
                }
            }
        }

        return result;
    }
}
