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
import org.springframework.http.server.ServletServerHttpRequest;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

/**
 * Filtration method factory
 */
public class FilterFactory {
    private static final Map<Class, FilterBuilder> filterList = initFilterList();

    private static Map<Class, FilterBuilder> initFilterList() {
        Map<Class, FilterBuilder> items = new HashMap<>();
        items.put(FieldFilterSetting.class, FieldFilter::new);
        items.put(FieldFilterSettings.class, FieldFilter::new);
        items.put(SessionStrategy.class, StrategyFilter::new);
        items.put(SessionStrategies.class, StrategyFilter::new);
        items.put(FileFilterSetting.class, FileFilter::new);
        return items;
    }

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
