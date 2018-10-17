package com.json.ignore;

import java.lang.annotation.*;

@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(value = JsonIgnoreSettings.class )
public @interface JsonIgnoreSetting {
    Class className() default void.class;
    String[] fields() default {};
}