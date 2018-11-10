package com.jfilter.filter;

import com.jfilter.request.RequestSession;
import org.springframework.core.MethodParameter;
import java.util.Arrays;

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
    public FilterFields getFields(Object object, RequestSession request) {
        FilterFields result = new FilterFields();

        for (FieldFilterSetting setting : config) {
            result.appendToMap(setting.className(), Arrays.asList(setting.fields()));
        }

        return result;
    }
}
