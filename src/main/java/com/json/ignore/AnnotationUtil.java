package com.json.ignore;

import com.json.ignore.strategy.SessionStrategies;
import com.json.ignore.strategy.SessionStrategy;
import java.lang.reflect.Method;

/**
 * This is util class used to help find annotations in class or method
 *
 */
public class AnnotationUtil {

    /**
     * Search for specified type list of annotation
     * @param method object's method which may have annotations
     * @param annotationClass name of annotation to search
     * @param <T> generic class
     * @return list of found annotations if found, else an array of length zero
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] getDeclaredAnnotations(Method method, Class annotationClass) {
        return (T[]) method.getDeclaredAnnotationsByType(annotationClass);
    }

    /**
     * Search for specified type of annotation
     * @param method object's method which may have annotation
     * @param annotationClass name of annotation to search
     * @param <T> generic class
     * @return annotation if found, else null
     */
    @SuppressWarnings("unchecked")
    public static <T> T getDeclaredAnnotation(Method method, Class annotationClass) {
        return (T) method.getDeclaredAnnotation(annotationClass);
    }

    /**
     * Check for annotations id declared in method
     * @param method object's method which may have annotation
     * @param annotationClasses name of annotation to search
     * @return if one of specified annotation is found, else returns false
     */
    public static boolean isAnnotationExists(Method method, Class... annotationClasses) {
        for (Class clazz : annotationClasses) {
            if (getDeclaredAnnotations(method, clazz).length > 0)
                return true;
        }
        return false;
    }

    /**
     * Search for {@link FieldIgnoreSetting} in method
     * @param method object's method which may have annotation
     * @return list of {@link FieldIgnoreSetting} if this type of annotation declared in method
     */
    public static FieldIgnoreSetting[] getSettingAnnotations(Method method) {
        FieldIgnoreSettings settings = AnnotationUtil.getDeclaredAnnotation(method, FieldIgnoreSettings.class);
        if (settings != null) {
            return  settings.value();
        } else
            return AnnotationUtil.getDeclaredAnnotations(method, FieldIgnoreSetting.class);
    }

    /**
     * Search for {@link SessionStrategy} in method
     * @param method bject's method which may have annotation
     * @return list of {@link SessionStrategy} if this type of annotation declared in method
     */
    public static SessionStrategy[] getStrategyAnnotations(Method method) {
        SessionStrategies strategies = AnnotationUtil.getDeclaredAnnotation(method, SessionStrategies.class);
        if(strategies != null) {
            return strategies.value();
        } else
            return  AnnotationUtil.getDeclaredAnnotation(method, SessionStrategy.class);
    }
}
