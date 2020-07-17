package com.jfilter.util;

import com.jfilter.components.DynamicSessionFilter;
import com.jfilter.filter.FilterFields;
import org.springframework.core.MethodParameter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T> T getClassDeclaredAnnotation(MethodParameter methodParameter, Class annotationClass) {
        return (T) methodParameter.getContainingClass().getDeclaredAnnotation(annotationClass);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T> T getMethodDeclaredAnnotation(MethodParameter methodParameter, Class annotationClass) {
        return (T) methodParameter.getMethod().getDeclaredAnnotation(annotationClass);
    }

    @SuppressWarnings("rawtypes")
    public static <T> List<T> getDeclaredAnnotations(MethodParameter methodParameter, Class annotationClass) {
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
