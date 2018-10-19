package com.json.ignore.filter.file;

import java.lang.annotation.*;

/**
 *
 * This annotation used for loading and parsing xml file annotated configurations
 */

@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FileFilterSetting {

    /**
     * File name
     * @return {@link String} file name
     * Default location id /resources/
     */
    String fileName() default "";
}
