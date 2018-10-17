package com.json.ignore.strategy;

import com.json.ignore.FieldIgnoreSetting;

import java.lang.annotation.*;

@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(SessionStrategies.class)
public @interface SessionStrategy {
    String attributeName() default "";
    String attributeValue() default "";
    FieldIgnoreSetting[] ignoreFields() default {};
}