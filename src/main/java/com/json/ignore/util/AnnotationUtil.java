package com.json.ignore.util;

import com.json.ignore.filter.field.FieldFilterSetting;
import com.json.ignore.filter.field.FieldFilterSettings;
import com.json.ignore.filter.file.FileConfig;
import com.json.ignore.filter.strategy.SessionStrategies;
import com.json.ignore.filter.strategy.SessionStrategy;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is util class used to help find annotations in class or method
 */

public final class AnnotationUtil {

    private AnnotationUtil() {
    }

    /**
     * Search for specified type list of annotation
     *
     * @param method          {@link Method} object's method which may have annotations
     * @param annotationClass {@link Annotation} name of annotation to search
     * @param <T>             {@link Annotation} generic class
     * @return {@link Annotation} list of found annotations if found, else an array of length zero
     */
    public static <T extends Annotation> T[] getDeclaredAnnotations(Method method, Class<T> annotationClass) {
        return method.getDeclaredAnnotationsByType(annotationClass);
    }

    /**
     * Search for specified type of annotation
     *
     * @param method          {@link Method} object's method which may have annotation
     * @param annotationClass {@link Annotation} name of annotation to search
     * @param <T>             {@link Annotation} generic class
     * @return {@link Annotation} annotation if found, else null
     */
    public static <T extends Annotation> T getDeclaredAnnotation(Method method, Class<T> annotationClass) {
        return method.getDeclaredAnnotation(annotationClass);
    }

    /**
     * Check for annotations id declared in method
     *
     * @param method            {@link Method} object's method which may have annotation
     * @param annotationClasses {@link Annotation} name of annotation to search
     * @param <T>               {@link Annotation}
     * @return {@link Annotation} if one of specified annotation is found, else returns false
     */
    public static <T extends Annotation> boolean isAnnotationExists(Method method, List<Class<T>> annotationClasses) {
        if (annotationClasses != null)
            for (Class<T> clazz : annotationClasses) {
                if (getDeclaredAnnotations(method, clazz).length > 0)
                    return true;
            }
        return false;
    }

    /**
     * Search for {@link FieldFilterSetting} in method
     *
     * @param method {@link Method} object's method which may have annotation
     * @return list of {@link FieldFilterSetting} if this type of annotation declared in method
     */
    public static FieldFilterSetting[] getSettingAnnotations(Method method) {
        FieldFilterSettings settings = AnnotationUtil.getDeclaredAnnotation(method, FieldFilterSettings.class);
        if (settings != null) {
            return settings.value();
        } else
            return AnnotationUtil.getDeclaredAnnotations(method, FieldFilterSetting.class);
    }

    /**
     * Search for {@link SessionStrategy} in method
     *
     * @param method {@link Method} object's method which may have annotation
     * @return list of {@link SessionStrategy} if this type of annotation declared in method
     */
    public static SessionStrategy[] getStrategyAnnotations(Method method) {
        SessionStrategies strategies = AnnotationUtil.getDeclaredAnnotation(method, SessionStrategies.class);
        if (strategies != null) {
            return strategies.value();
        } else
            return AnnotationUtil.getDeclaredAnnotations(method, SessionStrategy.class);
    }

    /**
     * Convert strategy class in Map
     *
     * @param strategy {@link FileConfig.Strategy} filter strategy
     * @return {@link HashMap} map of fields which should be filtered/excluded
     */
    public static Map<Class, List<String>> getStrategyFields(FileConfig.Strategy strategy) {
        Map<Class, List<String>> fields = new HashMap<>();

        if (strategy != null) {
            strategy.getFilters().forEach(filter -> {
                Class clazz = FileUtil.getClassByName(filter.getClassName());
                List<String> items;

                if (fields.containsKey(clazz)) {
                    items = fields.get(clazz);
                } else
                    items = new ArrayList<>();

                filter.getFields().forEach(field -> {
                    //filter duplicates of field names
                    if (!items.contains(field.getName()))
                        items.add(field.getName());
                });
                fields.put(clazz, items);
            });
        }
        return fields;
    }


}
