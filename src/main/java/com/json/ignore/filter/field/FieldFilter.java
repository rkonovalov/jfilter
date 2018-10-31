package com.json.ignore.filter.field;

import com.json.ignore.filter.BaseFilter;
import org.springframework.core.MethodParameter;
import org.springframework.http.server.ServerHttpRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class used for simple filtration of object's fields based on FieldFilterSetting configuration
 */
public class FieldFilter extends BaseFilter {

    /**
     * Array of {@link FieldFilterSetting} configuration annotations
     */
    private FieldFilterSetting[] config;

    /**
     * Creates a new instance of the {@link FieldFilter} class.
     *
     * @param methodParameter {@link MethodParameter} Rest method of Rest controller
     */
    public FieldFilter(MethodParameter methodParameter) {
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
        config = getRequestMethodParameter().getSettingAnnotations();
    }

    @Override
    public Map<Class, List<String>> getIgnoreList(Object object, ServerHttpRequest request) {
        Map<Class, List<String>> result = new HashMap<>();

        for (FieldFilterSetting setting : config) {
            appendToMap(result, setting.className(), Arrays.asList(setting.fields()));
        }

        return result;
    }
}
