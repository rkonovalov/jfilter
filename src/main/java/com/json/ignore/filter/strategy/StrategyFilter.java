package com.json.ignore.filter.strategy;

import com.json.ignore.filter.BaseFilter;
import com.json.ignore.filter.field.FieldFilterSetting;
import com.json.ignore.request.RequestSession;
import org.springframework.core.MethodParameter;
import org.springframework.http.server.ServerHttpRequest;

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
     * Constructor
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
    public Map<Class, List<String>> getIgnoreList(Object object, ServerHttpRequest request) {
        Map<Class, List<String>> result = new HashMap<>();
        RequestSession requestSession = new RequestSession(request);

        if (config != null) {
            for (SessionStrategy strategy : config) {
                if (requestSession.isSessionPropertyExists(strategy.attributeName(),
                        strategy.attributeValue())) {

                    for (FieldFilterSetting setting : strategy.ignoreFields()) {
                        appendToMap(result, setting.className(), Arrays.asList(setting.fields()));
                    }
                }
            }
        }
        return result;
    }
}
