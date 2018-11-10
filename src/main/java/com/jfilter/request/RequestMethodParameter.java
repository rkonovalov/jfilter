package com.jfilter.request;

import com.jfilter.filter.FieldFilterSetting;
import com.jfilter.filter.FieldFilterSettings;
import com.jfilter.filter.SessionStrategies;
import com.jfilter.filter.SessionStrategy;
import org.springframework.core.MethodParameter;
import java.lang.annotation.Annotation;


/**
 * Annotation request class
 * <p>
 * This is request class used to help find annotations in class or method
 */
public class RequestMethodParameter extends MethodParameter {

    /**
     * Creates a new instance of the {@link RequestMethodParameter} class.
     *
     * @param methodParameter {@link MethodParameter}
     */
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
    private  <T extends Annotation> T[] getDeclaredAnnotations(Class<T> annotationClass) {
        T[] annotations = getMethod().getDeclaredAnnotationsByType(annotationClass);
        Class<?> containingClass = getContainingClass();
        annotations = annotations.length != 0 ? annotations : containingClass.getDeclaredAnnotationsByType(annotationClass);
        return annotations;
    }

    /**
     * Get declared annotation
     *
     * <p>Attempts to find filter annotations in {@link MethodParameter} or in containing class
     *
     * @param annotationClass {@link Annotation} name of annotation to search
     * @param <T> {@link Annotation} generic class
     * @return {@link Annotation} annotation if found, else null
     */
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
}
