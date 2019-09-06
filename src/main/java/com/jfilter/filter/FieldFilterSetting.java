package com.jfilter.filter;


import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation used for configuring of field ignoring
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(value = FieldFilterSettings.class )
public @interface FieldFilterSetting {
    /**
     * Class name of filtering object
     *
     * @return {@link Class} class name of filterable class
     * May be null
     */
    Class className() default void.class;

    /**
     *
     * @return array of filterable items
     */
    String[] fields() default {};

    /**
     *
     * @return behaviour of filter {@link FilterBehaviour}
     */
    FilterBehaviour behaviour() default FilterBehaviour.HIDE_FIELDS;
}