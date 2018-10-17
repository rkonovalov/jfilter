package com.json.ignore.strategy;

import com.json.ignore.JsonIgnoreSetting;
import java.lang.annotation.*;

@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(JsonSessionStrategies.class)
public @interface JsonSessionStrategy {
    String attributeName() default "";
    String attributeValue() default "";
    JsonIgnoreSetting[] ignoreFields() default {};
}