package com.json.ignore.filter.strategy;

import com.json.ignore.filter.field.FieldFilterSetting;
import java.lang.annotation.*;

/**
 *
 * This annotation used for configuring of strategy of filtering
 */

@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(SessionStrategies.class)
public @interface SessionStrategy {
    /**
     *
     * @return {@link String} attribute name which will be find in HTTPSession attributes
     * May be null
     */
    String attributeName() default "";

    /**
     *
     * @return {@link String} attribute value
     * May be null
     */
    String attributeValue() default "";

    /**
     *
     * @return array of {@link FieldFilterSetting}  which configures of field filtering
     */
    FieldFilterSetting[] ignoreFields() default {};
}