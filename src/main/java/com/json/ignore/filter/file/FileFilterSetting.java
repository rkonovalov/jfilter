package com.json.ignore.filter.file;

import java.lang.annotation.*;

@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FileFilterSetting {
    String fileName() default "";
}
