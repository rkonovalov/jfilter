package com.jfilter.filter;

import java.lang.annotation.*;

/**
 * This annotation used for configuring of strategy of filtering
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(SessionStrategies.class)
public @interface SessionStrategy {
    /**
     *  Session parameter attribute name
     *
     * @return {@link String} attribute name which will be find in HTTPSession attributes
     * May be null
     */
    String attributeName() default "";

    /**
     * Session parameter attribute value
     *
     * @return {@link String} attribute value
     * May be null
     */
    String attributeValue() default "";

    /**
     * Array of {@link FieldFilterSetting}
     * @return array of {@link FieldFilterSetting}  which configures of field filtering
     */
    FieldFilterSetting[] ignoreFields() default {};
}