package com.json.ignore.filter.strategy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * Repeatable annotation of {@link SessionStrategy} interface
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SessionStrategies {
    /**
     *
     * @return array of {@link SessionStrategy}
     */
    SessionStrategy[] value();
}
