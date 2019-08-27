package com.jfilter.util;

import com.jfilter.components.DynamicSessionFilter;
import com.jfilter.filter.FilterFields;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
     * @param request {@link HttpServletRequest} http request
     * @param filterFields {@link FilterFields} instance of FilterFields
     */
    public static void useFilter(HttpServletRequest request, FilterFields filterFields) {
        request.setAttribute(DynamicSessionFilter.ATTRIBUTE_FILTER_FIELDS, filterFields);
    }

    /**
     * Sets filterFields object into http session ATTRIBUTE_FILTER_FIELDS attribute
     *
     * @param session {@link HttpSession} http session
     * @param filterFields {@link FilterFields} instance of FilterFields
     */
    public static void useFilter(HttpSession session, FilterFields filterFields) {
        session.setAttribute(DynamicSessionFilter.ATTRIBUTE_FILTER_FIELDS, filterFields);
    }
}
