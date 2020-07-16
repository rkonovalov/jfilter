package com.jfilter.filter;

import com.comparator.Comparator;
import com.jfilter.components.DynamicFilterProvider;
import com.jfilter.request.RequestSession;
import org.springframework.core.MethodParameter;

/**
 * Dynamic filter event
 *
 * <p>This event calls when {@link DynamicFilterProvider} attempts to
 * get {@link FilterFields} from inherited class
 */
public interface DynamicFilterEvent {

    /**
     * Request event handler
     *
     * @param comparator {@link Comparator} comparator object which will be used by {@link DynamicFilterProvider} for retrieve {@link FilterFields}
     */
    void onRequest(Comparator<RequestSession, FilterFields> comparator);
}
