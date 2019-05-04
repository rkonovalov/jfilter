package com.jfilter.mock.config;

import com.comparator.Comparator;
import com.jfilter.filter.DynamicFilterComponent;
import com.jfilter.filter.DynamicFilterEvent;
import com.jfilter.filter.FilterFields;
import com.jfilter.request.RequestSession;

@DynamicFilterComponent
public class MockDynamicNullFilter implements DynamicFilterEvent {

    @Override
    public void onRequest(Comparator<RequestSession, FilterFields> comparator) {

    }
}
