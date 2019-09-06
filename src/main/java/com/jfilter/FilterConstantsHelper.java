package com.jfilter;

/**
 * Filter constants class
 */
public class FilterConstantsHelper {

    public static final String MEDIA_TYPE_APPLICATION = "application";
    public static final String MEDIA_SUB_TYPE_JSON = "json";
    public static final String MEDIA_SUB_TYPE_JSON2 = "*+json";
    public static final String MEDIA_SUB_TYPE_XML = "xml";
    public static final String MEDIA_SUB_TYPE_XML2 = "*+xml";

    private FilterConstantsHelper() {
        throw new IllegalStateException("FilterConstants class");
    }
}
