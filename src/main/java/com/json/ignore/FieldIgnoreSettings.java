package com.json.ignore;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * Repeatable annotation of {@link FieldIgnoreSetting} interface
 */

@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldIgnoreSettings {

    /**
     *
     * @return array of {@link FieldIgnoreSetting}
     */
    FieldIgnoreSetting[] value();
}