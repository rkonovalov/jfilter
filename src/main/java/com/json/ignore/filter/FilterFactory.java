package com.json.ignore.filter;

import com.json.ignore.filter.field.FieldFilter;
import com.json.ignore.filter.field.FieldFilterSetting;
import com.json.ignore.filter.field.FieldFilterSettings;
import com.json.ignore.filter.file.FileFilter;
import com.json.ignore.filter.file.FileFilterSetting;
import com.json.ignore.filter.strategy.SessionStrategies;
import com.json.ignore.filter.strategy.SessionStrategy;
import com.json.ignore.filter.strategy.StrategyFilter;
import org.springframework.core.MethodParameter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

/**
 * Filters factory
 */

public class FilterFactory {
    /**
     * List of available filters
     */
    private static final Map<Class, FilterBuilder> filterList = initFilterList();

    /**
     * Filter list initialization
     * @return {@link HashMap} map of filters which can process specified annotations
     */
    private static Map<Class, FilterBuilder> initFilterList() {
        Map<Class, FilterBuilder> items = new HashMap<>();
        items.put(FieldFilterSetting.class, FieldFilter::new);
        items.put(FieldFilterSettings.class, FieldFilter::new);
        items.put(SessionStrategy.class, StrategyFilter::new);
        items.put(SessionStrategies.class, StrategyFilter::new);
        items.put(FileFilterSetting.class, FileFilter::new);
        return items;
    }

    /**
     * Retrieve filter from filter list by annotation defined in method
     * @param request {@link ServletServerHttpRequest} http request
     * @param methodParameter {@link MethodParameter} method
     * @return object instance of inherited class from {@link BaseFilter}
     */
    public static BaseFilter getFromFactory(ServletServerHttpRequest request, MethodParameter methodParameter) {
        for (Annotation annotation : methodParameter.getMethod().getDeclaredAnnotations()) {
            if (FilterFactory.filterList.containsKey(annotation.annotationType())) {
                return FilterFactory.filterList
                        .get(annotation.annotationType())
                        .build(request, methodParameter);
            }
        }
        return null;
    }

    /**
     * Retrieve filter from filter list by annotation defined in method
     * @param request {@link ServerHttpRequest} http request
     * @param methodParameter {@link MethodParameter} method
     * @return object instance of inherited class from {@link BaseFilter}
     */
    public static BaseFilter getFromFactory(ServerHttpRequest request, MethodParameter methodParameter) {
        return getFromFactory((ServletServerHttpRequest) request, methodParameter);
    }

    /**
     * Check if specified annotations, which accepted in FieldFilter or StrategyFilter classes is annotated in method
     *
     * @param methodParameter {@link MethodParameter} Rest method of Rest controller
     * @return true if specified annotations is found
     */
    public static boolean isAccept(MethodParameter methodParameter) {
        for (Annotation annotation : methodParameter.getMethod().getDeclaredAnnotations()) {
            if (FilterFactory.filterList.containsKey(annotation.annotationType())) {
                return true;
            }
        }
        return false;
    }

}
