package com.jfilter.mock.config;

import com.jfilter.filter.DynamicFilterComponent;
import com.jfilter.filter.DynamicFilterEvent;
import com.jfilter.filter.FilterFields;
import com.jfilter.request.RequestSession;
import org.springframework.core.MethodParameter;

@DynamicFilterComponent
public class MockDynamicNullFilter implements DynamicFilterEvent {
    @Override
    public FilterFields onGetFilterFields(MethodParameter methodParameter, RequestSession request) {
        return null;
    }
}
