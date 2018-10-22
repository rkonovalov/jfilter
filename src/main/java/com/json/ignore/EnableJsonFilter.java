package com.json.ignore;

import java.lang.annotation.*;

/**
 * Enables json filter
 * <p>
 * For enabling FilterProvider, FilterAdvice and all filter mechanisms this annotation should be specified on one of application beans,
 * as example on application configuration bean.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface EnableJsonFilter {
}