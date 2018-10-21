package com.json.ignore.filter.field;

import com.json.ignore.FieldAccessException;
import com.json.ignore.util.AnnotationUtil;
import com.json.ignore.filter.BaseFilter;
import com.json.ignore.util.SessionUtil;
import org.springframework.core.MethodParameter;
import org.springframework.http.server.ServerHttpRequest;
import javax.servlet.http.HttpSession;

/**
 * This class used for simple filtration of object's fields based on FieldFilterSetting configuration
 */
public class FieldFilter extends BaseFilter {

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
    protected void setConfig(MethodParameter methodParameter) {
        config = AnnotationUtil.getSettingAnnotations(methodParameter.getMethod());
    }

    /**
     * Attempt to filter object fields if filter annotations is configured
     * @param object {@link Object} object which fields will be filtered
     * @throws FieldAccessException exception throws on {@link IllegalAccessException}
     */
    @Override
    public void filter(Object object) throws FieldAccessException {
        if(object != null) {
            FieldFilterProcessor processor = new FieldFilterProcessor(config);
            processor.filterFields(object);
        }
    }

    /**
     * Attempt to filter object fields if filter annotations is configured
     * @param object {@link Object} object which fields will be filtered
     * @param session {@link HttpSession} session
     * @throws FieldAccessException exception throws on {@link IllegalAccessException}
     */
    @Override
    public void filter(Object object, HttpSession session) throws FieldAccessException {
        filter(object);
    }

    /**
     * Attempt to filter object fields if filter annotations is configured
     * @param object {@link Object} object which fields will be filtered
     * @param request {@link ServerHttpRequest} http request
     * @throws FieldAccessException exception throws on {@link IllegalAccessException}
     */
    @Override
    public void filter(Object object, ServerHttpRequest request) throws FieldAccessException {
        filter(object, SessionUtil.getSession(request));
    }
}
