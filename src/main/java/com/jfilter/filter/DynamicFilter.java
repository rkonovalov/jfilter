package com.jfilter.filter;

import java.lang.annotation.*;

import com.jfilter.advice.DynamicFilterProvider;

/**
 * This annotation used for configuring of dynamic filter
 *
 * <p>It is typically used in combination with annotation {@link DynamicFilterComponent} annotation.
 * {@link DynamicFilterProvider} attempts to get FilterFields from value;
 * Value should be class which implements {@link DynamicFilterEvent}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface DynamicFilter {
    Class<? extends DynamicFilterEvent> value();
}