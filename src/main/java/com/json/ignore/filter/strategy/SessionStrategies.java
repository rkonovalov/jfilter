package com.json.ignore.filter.strategy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Repeatable annotation of {@link SessionStrategy} interface
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SessionStrategies {
    /**
     *  Array of {@link SessionStrategy}
     *
     * @return array of {@link SessionStrategy}
     */
    SessionStrategy[] value();
}