package com.json.ignore.converter;

import com.json.ignore.advice.FilterAdvice;
import com.json.ignore.filter.FilterFields;

import java.io.Serializable;

/**
 * Class contains Serializable object and list of ignorable fields
 *
 * <p>Class used to send additional information from {@link FilterAdvice} to {@link FilterConverter}
 */
public class FilterClassWrapper implements Serializable {
    private static final long serialVersionUID = -6250969684778521840L;
    private final Serializable object;
    private final FilterFields filterFields;

    /**
     * Creates a new instance of the {@link FilterClassWrapper} class.
     *
     * @param object Serializable object
     * @param filterFields ignorable fields
     */
    public FilterClassWrapper(Serializable object, FilterFields filterFields) {
        this.object = object;
        this.filterFields = filterFields;
    }

    public Serializable getObject() {
        return object;
    }

    public FilterFields getIgnoreList() {
        return filterFields;
    }
}
