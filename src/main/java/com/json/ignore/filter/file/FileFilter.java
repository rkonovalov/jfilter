package com.json.ignore.filter.file;

import com.json.ignore.FieldAccessException;
import com.json.ignore.util.FileUtil;
import com.json.ignore.util.AnnotationUtil;
import com.json.ignore.filter.BaseFilter;
import com.json.ignore.util.SessionUtil;
import com.json.ignore.filter.field.FieldFilterProcessor;
import org.springframework.core.MethodParameter;
import org.springframework.http.server.ServerHttpRequest;
import javax.servlet.http.HttpSession;

/**
 * This class used for filtration of object's fields based on xml file configuration
 */
public class FileFilter extends BaseFilter {
    private FileConfig fileConfig;
    private Class controllerClass;

    /**
     * Constructor
     *
     * @param serverHttpRequest {@link ServerHttpRequest} http request
     * @param methodParameter   {@link MethodParameter} method
     */
    public FileFilter(ServerHttpRequest serverHttpRequest, MethodParameter methodParameter) {
        super(serverHttpRequest);
        setConfig(methodParameter);
    }

    /**
     * Constructor
     *
     * @param session         {@link HttpSession} http session
     * @param methodParameter {@link MethodParameter} method
     */
    public FileFilter(HttpSession session, MethodParameter methodParameter) {
        super(session);
        setConfig(methodParameter);
    }

    /**
     * Attempt to deserialize xml file to {@link FileConfig}
     *
     * @param fileName {@link String} file name
     * @return {@link FileConfig} config
     */
    private FileConfig parseFile(String fileName) {
        return FileUtil.xmlFileToClass(FileUtil.resourceFile(fileName), FileConfig.class);
    }

    /**
     * Attempt to retrieve all FieldFilterSetting annotations from method
     *
     * @param methodParameter {@link MethodParameter} method parameter
     */
    @Override
    protected void setConfig(MethodParameter methodParameter) {
        controllerClass = methodParameter.getContainingClass();
        FileFilterSetting config = AnnotationUtil.getDeclaredAnnotation(methodParameter.getMethod(), FileFilterSetting.class);
        fileConfig = parseFile(config.fileName());
    }

    /**
     * Get class where method was initiated
     *
     * @return {@link Class} class
     */
    public Class getControllerClass() {
        return controllerClass;
    }

    /**
     * Set class where method was initiated. Used in unit tests
     *
     * @param controllerClass {@link Class} class
     * @return {@link FileFilter} current instance
     */
    public FileFilter setControllerClass(Class controllerClass) {
        this.controllerClass = controllerClass;
        return this;
    }

    /**
     * Attempt to filter object fields if filter annotations is configured
     *
     * @param object {@link Object} object which fields will be filtered
     * @throws FieldAccessException exception throws on {@link IllegalAccessException}
     */
    @Override
    public void filter(Object object) throws FieldAccessException {
        filter(object, this.getSession());
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

    /**
     * Attempt to filter object fields if filter annotations is configured
     * @param object {@link Object} object which fields will be filtered
     * @param session {@link HttpSession} session
     * @throws FieldAccessException exception throws on {@link IllegalAccessException}
     */
    @Override
    public void filter(Object object, HttpSession session) throws FieldAccessException {
        if (object != null && fileConfig != null) {
            for (FileConfig.Controller controller : fileConfig.getControllers()) {
                if (controllerClass.getName().equals(controller.getClassName()) && controller.getStrategies() != null) {
                    controller.getStrategies().forEach(strategy -> {
                        if (SessionUtil.isSessionPropertyExists(session, strategy.getAttributeName(), strategy.getAttributeValue())) {
                            FieldFilterProcessor processor = new FieldFilterProcessor(AnnotationUtil.getStrategyFields(strategy));
                            processor.filterFields(object);
                        }
                    });
                }
            }
        }
    }


}
