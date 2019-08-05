package com.jfilter.util;

import com.jfilter.components.DynamicSessionFilter;
import com.jfilter.filter.FilterFields;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class FilterUtil {

    public static void useFilter(HttpServletRequest request, FilterFields filterFields) {
        request.setAttribute(DynamicSessionFilter.ATTRIBUTE_FILTER_FIELDS, filterFields);
    }

    public static void useFilter(HttpSession session, FilterFields filterFields) {
        session.setAttribute(DynamicSessionFilter.ATTRIBUTE_FILTER_FIELDS, filterFields);
    }
}
