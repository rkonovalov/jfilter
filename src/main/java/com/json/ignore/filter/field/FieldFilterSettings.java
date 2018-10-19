package com.json.ignore.filter.field;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * Repeatable annotation of {@link FieldFilterSetting} interface
 */

@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldFilterSettings {

    /**
     *
     * @return array of {@link FieldFilterSetting}
     */
    FieldFilterSetting[] value();
}