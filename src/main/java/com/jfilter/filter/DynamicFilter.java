package com.jfilter.filter;

import com.jfilter.components.DynamicFilterProvider;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation used for configuring of dynamic filter
 *
 * <p>It is typically used in combination with annotation {@link DynamicFilterComponent} annotation.
 * {@link DynamicFilterProvider} attempts to get FilterFields from value;
 * Value should be class which implements {@link DynamicFilterEvent}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.TYPE})
@Documented
public @interface DynamicFilter {
    Class<? extends DynamicFilterEvent> value();
}