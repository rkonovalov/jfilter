package com.json.ignore;

import com.json.ignore.strategy.JsonSessionStrategies;
import com.json.ignore.strategy.JsonSessionStrategy;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class AnnotationUtil {

    public static Annotation[] getDeclaredAnnotation(Class clazz, Class annotationClass) {
        return clazz.getDeclaredAnnotationsByType(annotationClass);
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] getDeclaredAnnotations(Method method, Class annotationClass) {
        return (T[]) method.getDeclaredAnnotationsByType(annotationClass);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getDeclaredAnnotation(Method method, Class annotationClass) {
        return (T) method.getDeclaredAnnotation(annotationClass);
    }

    public static boolean isAnnotationExists(Method method, Class... annotationClasses) {
        for (Class clazz : annotationClasses) {
            if (getDeclaredAnnotations(method, clazz).length > 0)
                return true;
        }
        return false;
    }

    public static JsonIgnoreSetting[] getSettingAnnotations(Method method) {
        JsonIgnoreSettings settings = AnnotationUtil.getDeclaredAnnotation(method, JsonIgnoreSettings.class);
        if (settings != null) {
            return  settings.value();
        } else
            return AnnotationUtil.getDeclaredAnnotations(method, JsonIgnoreSetting.class);
    }

    public static JsonSessionStrategy[] getStrategyAnnotations(Method method) {
        JsonSessionStrategies strategies = AnnotationUtil.getDeclaredAnnotation(method, JsonSessionStrategies.class);
        if(strategies != null) {
            return strategies.value();
        } else
            return  AnnotationUtil.getDeclaredAnnotation(method, JsonSessionStrategy.class);
    }
}
