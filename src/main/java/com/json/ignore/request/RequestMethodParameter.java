package com.json.ignore.request;

import com.json.ignore.filter.field.FieldFilterSetting;
import com.json.ignore.filter.field.FieldFilterSettings;
import com.json.ignore.filter.strategy.SessionStrategies;
import com.json.ignore.filter.strategy.SessionStrategy;
import org.springframework.core.MethodParameter;

import java.lang.annotation.Annotation;


/**
 * Annotation request class
 * <p>
 * This is request class used to help find annotations in class or method
 */
public class RequestMethodParameter extends MethodParameter {

    public RequestMethodParameter(MethodParameter methodParameter) {
        super(methodParameter);
    }

    /**
     * Search for specified type list of annotation
     *
     * @param annotationClass {@link Annotation} name of annotation to search
     * @param <T>             {@link Annotation} generic class
     * @return {@link Annotation} list of found annotations if found, else an array of length zero
     */
    public <T extends Annotation> T[] getDeclaredAnnotations(Class<T> annotationClass) {
        T[] annotations = getMethod().getDeclaredAnnotationsByType(annotationClass);
        Class<?> containingClass = getContainingClass();
        annotations = annotations.length != 0 ? annotations : containingClass.getDeclaredAnnotationsByType(annotationClass);
        return annotations;
    }

    public <T extends Annotation> T getDeclaredAnnotation(Class<T> annotationClass) {
        //Get annotation from method
        T annotation = getMethod().getDeclaredAnnotation(annotationClass);
        //If annotation is null try to get annotation from containing class
        annotation = annotation != null ? annotation : getContainingClass().getDeclaredAnnotation(annotationClass);

        return annotation;
    }

    /**
     * Search for {@link FieldFilterSetting} in method
     *
     * @return list of {@link FieldFilterSetting} if this type of annotation declared in method
     */
    public FieldFilterSetting[] getSettingAnnotations() {
        FieldFilterSettings settings = getDeclaredAnnotation(FieldFilterSettings.class);
        if (settings != null) {
            return settings.value();
        } else
            return getDeclaredAnnotations(FieldFilterSetting.class);
    }

    /**
     * Search for {@link SessionStrategy} in method
     *
     * @param methodParameter {@link MethodParameter} object's method which may have annotation
     * @return list of {@link SessionStrategy} if this type of annotation declared in method
     */
    public SessionStrategy[] getStrategyAnnotations(MethodParameter methodParameter) {
        SessionStrategies strategies = getDeclaredAnnotation(SessionStrategies.class);
        if (strategies != null) {
            return strategies.value();
        } else
            return getDeclaredAnnotations(SessionStrategy.class);
    }

    /**
     * Gets class by name
     * <P>
     * Try to get class by it full name. If class couldn't be found, returns null
     * @param className {@link String} class name. Example: java.io.File
     * @return {@link Class} return class, else null
     */
    public static Class getClassByName(String className) {
        if (className != null && !className.isEmpty()) {
            try {
                return Class.forName(className);
            } catch (ClassNotFoundException e) {
                return null;
            }
        } else
            return null;
    }
}
