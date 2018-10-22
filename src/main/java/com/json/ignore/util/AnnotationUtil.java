package com.json.ignore.util;

import com.json.ignore.filter.field.FieldFilterSetting;
import com.json.ignore.filter.field.FieldFilterSettings;
import com.json.ignore.filter.file.FileConfig;
import com.json.ignore.filter.strategy.SessionStrategies;
import com.json.ignore.filter.strategy.SessionStrategy;
import org.springframework.core.MethodParameter;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Annotation util class
 * <p>
 * This is util class used to help find annotations in class or method
 */
public final class AnnotationUtil {

    protected AnnotationUtil() {
    }

    /**
     * Search for specified type list of annotation
     *
     * @param methodParameter {@link MethodParameter} object's method which may have annotations
     * @param annotationClass {@link Annotation} name of annotation to search
     * @param <T>             {@link Annotation} generic class
     * @return {@link Annotation} list of found annotations if found, else an array of length zero
     */
    public static <T extends Annotation> T[] getDeclaredAnnotations(MethodParameter methodParameter, Class<T> annotationClass) {
        T[] annotations = methodParameter.getMethod().getDeclaredAnnotationsByType(annotationClass);
        Class<?> containingClass = methodParameter.getContainingClass();
        annotations = annotations.length != 0 ? annotations : containingClass.getDeclaredAnnotationsByType(annotationClass);
        return annotations;
    }

    public static <T extends Annotation> T getDeclaredAnnotation(MethodParameter methodParameter, Class<T> annotationClass) {
        //Get annotation from method
        T annotation = methodParameter.getMethod().getDeclaredAnnotation(annotationClass);
        //If annotation is null try to get annotation from containing class
        annotation = annotation != null ? annotation : methodParameter.getContainingClass().getDeclaredAnnotation(annotationClass);

        return annotation;
    }

    /**
     * Check for annotations id declared in method
     *
     * @param methodParameter   {@link MethodParameter} object's method which may have annotation
     * @param annotationClasses {@link Annotation} name of annotation to search
     * @param <T>               {@link Annotation}
     * @return {@link Annotation} if one of specified annotation is found, else returns false
     */
    public static <T extends Annotation> boolean isAnnotationExists(MethodParameter methodParameter, List<Class<T>> annotationClasses) {
        if (annotationClasses != null)
            for (Class<T> clazz : annotationClasses) {
                if (getDeclaredAnnotations(methodParameter, clazz).length > 0)
                    return true;
            }
        return false;
    }

    /**
     * Search for {@link FieldFilterSetting} in method
     *
     * @param methodParameter {@link MethodParameter} object's method which may have annotation
     * @return list of {@link FieldFilterSetting} if this type of annotation declared in method
     */
    public static FieldFilterSetting[] getSettingAnnotations(MethodParameter methodParameter) {
        FieldFilterSettings settings = AnnotationUtil.getDeclaredAnnotation(methodParameter, FieldFilterSettings.class);
        if (settings != null) {
            return settings.value();
        } else
            return AnnotationUtil.getDeclaredAnnotations(methodParameter, FieldFilterSetting.class);
    }

    /**
     * Search for {@link SessionStrategy} in method
     *
     * @param methodParameter {@link MethodParameter} object's method which may have annotation
     * @return list of {@link SessionStrategy} if this type of annotation declared in method
     */
    public static SessionStrategy[] getStrategyAnnotations(MethodParameter methodParameter) {
        SessionStrategies strategies = AnnotationUtil.getDeclaredAnnotation(methodParameter, SessionStrategies.class);
        if (strategies != null) {
            return strategies.value();
        } else
            return AnnotationUtil.getDeclaredAnnotations(methodParameter, SessionStrategy.class);
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
