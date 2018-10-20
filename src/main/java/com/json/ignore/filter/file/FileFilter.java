package com.json.ignore.filter.file;

import com.json.ignore.FieldAccessException;
import com.json.ignore.filter.FileUtil;
import com.json.ignore.filter.AnnotationUtil;
import com.json.ignore.filter.BaseFilter;
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
     * Attempt to filter/exclude object's fields if filter annotations is configured
     *
     * @param object {@link Object} object which fields will be filtered
     * @throws FieldAccessException exception of illegal access
     */
    @Override
    public void filter(Object object) throws FieldAccessException {
        if (object != null && fileConfig.getControllers() != null) {
            for (FileConfig.Controller controller : fileConfig.getControllers()) {
                if (controllerClass.getName().equals(controller.getClassName()) && controller.getStrategies() != null) {
                    controller.getStrategies().forEach(strategy -> {
                        if (isSessionPropertyExists(strategy.getAttributeName(), strategy.getAttributeValue())) {
                            FieldFilterProcessor processor = new FieldFilterProcessor(AnnotationUtil.getStrategyFields(strategy));
                            processor.filterFields(object);
                        }
                    });
                }
            }
        }
    }


}
