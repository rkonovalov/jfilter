package com.jfilter.converter;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.jfilter.filter.FilterFields;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import static com.jfilter.filter.FilterBehaviour.KEEP_FIELDS;

/**
 * Filter used for filtering fields from object
 */
@JsonFilter("com.jfilter.converter.MixinFilter")
public class MixinFilter extends SimpleBeanPropertyFilter implements Serializable {
    private static final long serialVersionUID = 1780940093765526265L;

    private final FilterFields filterFields;

    public MixinFilter(FilterFields filterFields) {
        this.filterFields = filterFields;
    }

    @Override
    protected boolean include(BeanPropertyWriter writer) {
        return !isFilterable(writer.getMember().getDeclaringClass(), writer.getName());
    }

    @Override
    protected boolean include(PropertyWriter writer) {
        return !isFilterable(writer.getMember().getDeclaringClass(), writer.getName());
    }

    private boolean fieldSpecified(List<Class> classes, String fieldName) {
        return classes.stream()
                .anyMatch(clazz -> fieldSpecified(clazz, fieldName));
    }

    private boolean fieldSpecified(Class clazz, String fieldName) {
        List<String> classFields = filterFields.getFieldsMap().get(clazz);

        if (classFields != null) {
            return classFields.stream()
                    .anyMatch(field -> field.equals(fieldName));
        } else
            return false;
    }

    private boolean isFilterable(Class clazz, String fieldName) {
        boolean fieldFound = fieldSpecified(Arrays.asList(void.class, clazz), fieldName);
        return (filterFields.getFilterBehaviour() == KEEP_FIELDS) != fieldFound;
    }
}
