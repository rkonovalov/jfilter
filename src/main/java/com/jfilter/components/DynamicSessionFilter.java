package com.jfilter.components;

import com.comparator.Comparator;
import com.jfilter.filter.DynamicFilterComponent;
import com.jfilter.filter.DynamicFilterEvent;
import com.jfilter.filter.FilterFields;
import com.jfilter.request.RequestSession;

/**
 * Dynamic session filter
 *
 * <p>
 * This filter get FilterFields from session's or request's attributes if it exist
 */
@SuppressWarnings("WeakerAccess")
@DynamicFilterComponent
public class DynamicSessionFilter implements DynamicFilterEvent {
    public static final String ATTRIBUTE_FILTER_FIELDS = "ATTRIBUTE_FILTER_FIELDS";

    /**
     * Request event handler
     *
     * <p>Retrieve FilterFields object from session attributes by name ATTRIBUTE_FILTER_FIELDS if it instance of FilterFields
     *
     * @param comparator {@link Comparator} comparator object which will be used by {@link DynamicFilterProvider} for retrieve {@link FilterFields}
     */
    @Override
    public void onRequest(Comparator<RequestSession, FilterFields> comparator) {

        comparator
                //Check and use if FilterFields exists in request
                .compare((request -> FilterFields.class.isInstance(request.getRequest().getAttribute(ATTRIBUTE_FILTER_FIELDS))),
                (result -> (FilterFields) result.getRequest().getAttribute(ATTRIBUTE_FILTER_FIELDS)))

                //Check and use if FilterFields exists in session
                .compare((request -> FilterFields.class.isInstance(request.getSession().getAttribute(ATTRIBUTE_FILTER_FIELDS))),
                (result -> (FilterFields) result.getSession().getAttribute(ATTRIBUTE_FILTER_FIELDS)))
        ;
    }
}
