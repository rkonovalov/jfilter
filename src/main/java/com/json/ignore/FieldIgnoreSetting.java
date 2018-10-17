package com.json.ignore;

import java.lang.annotation.*;

/**
 *
 * This annotation used for configuring of field ignoring
 */

@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(value = FieldIgnoreSettings.class )
public @interface FieldIgnoreSetting {
    /**
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
}