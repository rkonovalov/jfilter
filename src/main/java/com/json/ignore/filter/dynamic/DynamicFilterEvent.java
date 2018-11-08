package com.json.ignore.filter.dynamic;

import com.json.ignore.advice.DynamicFilterProvider;
import com.json.ignore.filter.FilterFields;
import com.json.ignore.request.RequestSession;
import org.springframework.core.MethodParameter;

/**
 * Dynamic filter event
 *
 * <p>This event calls when {@link DynamicFilterProvider} attempts to
 * get {@link FilterFields} from inherited class
 */
public interface DynamicFilterEvent {
    FilterFields onGetFilterFields(MethodParameter methodParameter, RequestSession request);
}
