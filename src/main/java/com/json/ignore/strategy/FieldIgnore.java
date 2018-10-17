package com.json.ignore.strategy;

import com.json.ignore.AnnotationUtil;
import com.json.ignore.FieldIgnoreSetting;
import com.json.ignore.FieldIgnoreProcessor;
import com.json.ignore.FieldIgnoreSettings;
import org.springframework.core.MethodParameter;
import org.springframework.http.server.ServerHttpRequest;

/**
 * This class used for simple filtration of object's fields based on FieldIgnoreSetting configuration
 */
public class FieldIgnore extends Ignore {

    /**
     * Array of {@link FieldIgnoreSetting} configuration annotations
     */
    private FieldIgnoreSetting[] config;

    /**
     * Constructor
     * @param serverHttpRequest {@link ServerHttpRequest} servlet request
     * @param methodParameter {@link MethodParameter} Rest method of Rest controller
     */
    public FieldIgnore(ServerHttpRequest serverHttpRequest, MethodParameter methodParameter) {
        super(serverHttpRequest);
        /*
         * Attempt to retrieve all FieldIgnoreSetting annotations from method
         */
        config = AnnotationUtil.getSettingAnnotations(methodParameter.getMethod());
    }

    /**
     * Filter object's fields if filtrating annotations is configured
     * @param object {@link Object} object which fields will be filtered
     * @throws IllegalAccessException exception of illegal access
     */
    @Override
    public void jsonIgnore(Object object) throws IllegalAccessException {
        FieldIgnoreProcessor jsonIgnore = new FieldIgnoreProcessor(config);
        jsonIgnore.ignoreFields(object);
    }

    /**
     * Used to show that method has {@link FieldIgnoreSetting} or {@link FieldIgnoreSettings} annotations
     * @param methodParameter {@link MethodParameter} Rest method of Rest controller
     * @return {@link Boolean} true if annotations found, otherwise false
     */
    public static boolean isAccept(MethodParameter methodParameter) {
        return AnnotationUtil.isAnnotationExists(methodParameter.getMethod(), FieldIgnoreSetting.class, FieldIgnoreSettings.class);
    }
}
