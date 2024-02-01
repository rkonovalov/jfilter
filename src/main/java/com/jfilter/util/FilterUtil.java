package com.jfilter.util;

import com.jfilter.components.DynamicSessionFilter;
import com.jfilter.filter.DynamicFilter;
import com.jfilter.filter.FilterFields;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Filter utils class
 */
public class FilterUtil {

    private FilterUtil() {
        throw new IllegalStateException("FilterUtil class");
    }

    /**
     * Sets filterFields object into http request ATTRIBUTE_FILTER_FIELDS attribute
     *
     * @param request      {@link HttpServletRequest} http request
     * @param filterFields {@link FilterFields} instance of FilterFields
     */
    public static void useFilter(HttpServletRequest request, FilterFields filterFields) {
        request.setAttribute(DynamicSessionFilter.ATTRIBUTE_FILTER_FIELDS, filterFields);
    }

    /**
     * Sets filterFields object into http session ATTRIBUTE_FILTER_FIELDS attribute
     *
     * @param session      {@link HttpSession} http session
     * @param filterFields {@link FilterFields} instance of FilterFields
     */
    public static void useFilter(HttpSession session, FilterFields filterFields) {
        session.setAttribute(DynamicSessionFilter.ATTRIBUTE_FILTER_FIELDS, filterFields);
    }

    /**
     * Extract DynamicFilter from class annotated with  DynamicFilter annotation and  containing current method
     *
     * @param methodParameter method which contains in class annotated with {@link RestController}
     * @param annotationClass class inherited from {@link DynamicFilter}
     * @param <T> generic of {@link DynamicFilter}
     * @return object inherited from {@link DynamicFilter}
     */
    @SuppressWarnings("unchecked")
    public static <T> T getClassDeclaredAnnotation(MethodParameter methodParameter, Class<? extends DynamicFilter> annotationClass) {
        return (T) methodParameter.getContainingClass().getDeclaredAnnotation(annotationClass);
    }

    /**
     * Extract DynamicFilter from method annotated with DynamicFilter annotation
     *
     * @param methodParameter method which contains in class annotated with {@link RestController}
     * @param annotationClass class inherited from {@link DynamicFilter}
     * @param <T> generic of {@link DynamicFilter}
     * @return object inherited from {@link DynamicFilter}
     */
    @SuppressWarnings("unchecked")
    public static <T> T getMethodDeclaredAnnotation(MethodParameter methodParameter, Class<? extends DynamicFilter> annotationClass) {
        return (T) methodParameter.getMethod().getDeclaredAnnotation(annotationClass);
    }

    public static <T> List<T> getDeclaredAnnotations(MethodParameter methodParameter, Class<? extends DynamicFilter> annotationClass) {
        List<T> annotations = new ArrayList<>();
        T classAnnotation = getClassDeclaredAnnotation(methodParameter, annotationClass);
        T methodAnnotation = getMethodDeclaredAnnotation(methodParameter, annotationClass);

        if (classAnnotation != null)
            annotations.add(classAnnotation);
        if (methodAnnotation != null)
            annotations.add(methodAnnotation);

        return annotations;
    }
}
