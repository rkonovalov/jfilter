package com.jfilter.components;

import com.jfilter.filter.DynamicFilterComponent;
import com.jfilter.filter.DynamicFilterEvent;
import com.jfilter.filter.FilterFields;
import com.jfilter.request.RequestSession;
import org.springframework.core.MethodParameter;

import java.util.Optional;

import static com.jfilter.filter.FilterFields.EMPTY_FIELDS;

/**
 * Dynamic session filter
 *
 * <p>This filter get FilterFields from session if it exist
 */
@SuppressWarnings("WeakerAccess")
@DynamicFilterComponent
public class DynamicSessionFilter implements DynamicFilterEvent {
    public static final String ATTRIBUTE_FILTER_FIELDS = "ATTRIBUTE_FILTER_FIELDS";

    /**
     * Retrieves {@link FilterFields} from session
     *
     * <p>Method attempt to retrieve FilterFields from session if it exist
     *
     * @param methodParameter method parameter
     * @param request         service request
     * @return {@link FilterFields} if found in session attributes, otherwise returns empty FilterFields
     */
    @Override
    public FilterFields onGetFilterFields(MethodParameter methodParameter, RequestSession request) {
        return Optional.of(request.getSession())
                .filter(s -> FilterFields.class.isInstance(s.getAttribute(ATTRIBUTE_FILTER_FIELDS)))
                .map(s -> (FilterFields) s.getAttribute(ATTRIBUTE_FILTER_FIELDS))
                .orElse(EMPTY_FIELDS);
    }
}
