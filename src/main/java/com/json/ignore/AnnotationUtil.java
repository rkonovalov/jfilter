package com.json.ignore;

import com.json.ignore.strategy.SessionStrategies;
import com.json.ignore.strategy.SessionStrategy;
import java.lang.reflect.Method;

public class AnnotationUtil {

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

    public static FieldIgnoreSetting[] getSettingAnnotations(Method method) {
        FieldIgnoreSettings settings = AnnotationUtil.getDeclaredAnnotation(method, FieldIgnoreSettings.class);
        if (settings != null) {
            return  settings.value();
        } else
            return AnnotationUtil.getDeclaredAnnotations(method, FieldIgnoreSetting.class);
    }

    public static SessionStrategy[] getStrategyAnnotations(Method method) {
        SessionStrategies strategies = AnnotationUtil.getDeclaredAnnotation(method, SessionStrategies.class);
        if(strategies != null) {
            return strategies.value();
        } else
            return  AnnotationUtil.getDeclaredAnnotation(method, SessionStrategy.class);
    }
}
