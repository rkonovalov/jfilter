package com.json.ignore.filter.dynamic;

import com.json.ignore.filter.FilterFields;
import com.json.ignore.request.RequestSession;
import org.springframework.core.MethodParameter;

/**
 * Dynamic session filter
 *
 * <p>This filter get FilterFields from session if it exist
 */
@SuppressWarnings("WeakerAccess")
@DynamicFilterComponent
public class DynamicSession implements DynamicFilterEvent {
    public static final String ATTRIBUTE_FILTER_FIELDS = "ATTRIBUTE_FILTER_FIELDS";

    @Override
    public FilterFields onGetFilterFields(MethodParameter methodParameter, RequestSession request) {

        if (FilterFields.class.isInstance(request.getSession().getAttribute(ATTRIBUTE_FILTER_FIELDS))) {
            return (FilterFields) request.getSession().getAttribute(ATTRIBUTE_FILTER_FIELDS);
        } else
            return new FilterFields();
    }
}
