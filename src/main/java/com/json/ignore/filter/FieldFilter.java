package com.json.ignore.filter;

import com.json.ignore.AnnotationUtil;
import org.springframework.core.MethodParameter;
import org.springframework.http.server.ServerHttpRequest;

import javax.servlet.http.HttpSession;

/**
 * This class used for simple filtration of object's fields based on FieldFilterSetting configuration
 */
public class FieldFilter extends Filter {

    /**
     * Array of {@link FieldFilterSetting} configuration annotations
     */
    private FieldFilterSetting[] config;

    /**
     * Constructor
     * @param serverHttpRequest {@link ServerHttpRequest} servlet request
     * @param methodParameter {@link MethodParameter} Rest method of Rest controller
     */
    public FieldFilter(ServerHttpRequest serverHttpRequest, MethodParameter methodParameter) {
        super(serverHttpRequest);
        setConfig(methodParameter);
    }

    /**
     * Constructor
     * @param session {@link HttpSession} session
     * @param methodParameter {@link MethodParameter} Rest method of Rest controller
     */
    public FieldFilter(HttpSession session, MethodParameter methodParameter) {
        super(session);
        setConfig(methodParameter);
    }

    /**
     * Attempt to retrieve all FieldFilterSetting annotations from method
     * @param methodParameter {@link MethodParameter} method parameter
     */
    @Override
    public void setConfig(MethodParameter methodParameter) {
        config = AnnotationUtil.getSettingAnnotations(methodParameter.getMethod());
    }

    /**
     * Filter object's fields if filtrating annotations is configured
     * @param object {@link Object} object which fields will be filtered
     * @throws IllegalAccessException exception of illegal access
     */
    @Override
    public void filter(Object object) throws IllegalAccessException {
        FieldFilterProcessor processor = new FieldFilterProcessor(config);
        processor.filterFields(object);
    }

    /**
     * Used to show that method has {@link FieldFilterSetting} or {@link FieldFilterSettings} annotations
     * @param methodParameter {@link MethodParameter} Rest method of Rest controller
     * @return {@link Boolean} true if annotations found, otherwise false
     */
    public static boolean isAccept(MethodParameter methodParameter) {
        return AnnotationUtil.isAnnotationExists(methodParameter.getMethod(), FieldFilterSetting.class, FieldFilterSettings.class);
    }
}
