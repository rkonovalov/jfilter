package com.json.ignore.advice;

import com.json.ignore.filter.FilterFields;
import com.json.ignore.filter.dynamic.DynamicFilterComponent;
import com.json.ignore.filter.dynamic.DynamicFilterEvent;
import com.json.ignore.filter.dynamic.DynamicFilter;
import com.json.ignore.request.RequestSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Dynamic filter provider
 *
 * <p>This component finds and provides Dynamic filters which annotated by {@link DynamicFilterComponent} annotation.
 * Could be used for retrieving {@link FilterFields} from {@link MethodParameter} which annotated by {@link DynamicFilter}
 */
@Component
public class DynamicFilterProvider {
    private final ApplicationContext applicationContext;
    private final Map<Class, DynamicFilterEvent> dynamicList;

    @Autowired
    public DynamicFilterProvider(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.dynamicList = new ConcurrentHashMap<>();
        findDynamicFilters();
    }

    private void findDynamicFilters() {
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(DynamicFilterComponent.class);

        beans.forEach((k, v) -> {
            if (DynamicFilterEvent.class.isInstance(v))
                dynamicList.put(v.getClass(), (DynamicFilterEvent) v);
        });
    }

    public FilterFields getFields(MethodParameter methodParameter, RequestSession request) {
        DynamicFilter dynamicFilterAnnotation = methodParameter.getMethod().getDeclaredAnnotation(DynamicFilter.class);

        if (dynamicList.containsKey(dynamicFilterAnnotation.value())) {
            DynamicFilterEvent filter = dynamicList.get(dynamicFilterAnnotation.value());
            return filter.onGetFilterFields(methodParameter, request);
        } else
            return null;
    }
}
