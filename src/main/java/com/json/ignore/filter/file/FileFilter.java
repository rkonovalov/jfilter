package com.json.ignore.filter.file;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.json.ignore.FieldAccessException;
import com.json.ignore.util.FileUtil;
import com.json.ignore.util.AnnotationUtil;
import com.json.ignore.filter.BaseFilter;
import com.json.ignore.util.SessionUtil;
import com.json.ignore.filter.field.FieldFilterProcessor;
import org.springframework.core.MethodParameter;
import org.springframework.http.server.ServerHttpRequest;

import java.io.File;
import java.io.IOException;

/**
 * This class used for filtration of object's fields based on xml file configuration
 */
public class FileFilter extends BaseFilter {
    private FileConfig config;
    private Class controllerClass;

    /**
     * Constructor
     *
     * @param methodParameter {@link MethodParameter} method
     */
    public FileFilter(MethodParameter methodParameter) {
        super(methodParameter);
        setConfig(methodParameter);
    }

    /**
     * Attempt to deserialize xml file to {@link FileConfig}
     *
     * @param fileName {@link String} file name
     * @return {@link FileConfig} config
     */
    private FileConfig parseFile(String fileName) {
        return xmlFileToClass(FileUtil.resourceFile(fileName), FileConfig.class);
    }

    /**
     * Convert cml file to Class
     * <p>
     * Deserialize xml file to Class
     * @param file {@link File} file from resource name if file exist
     * @param clazz  {@link Class} class type
     * @param <T> the type of the value being boxed
     * @return {@link Object} returns instantiated object type of specified class
     * @throws FieldAccessException {@link FieldAccessException} when caught {@link IOException}
     */
    private <T> T xmlFileToClass(File file, Class<T> clazz) throws FieldAccessException {
        try {
            return file != null ? new XmlMapper().readValue(file, clazz) : null;
        } catch (IOException e) {
            throw new FieldAccessException(e);
        }
    }

    /**
     * Attempt to retrieve all FieldFilterSetting annotations from method
     *
     * @param methodParameter {@link MethodParameter} method parameter
     */
    @Override
    protected void setConfig(MethodParameter methodParameter) {
        controllerClass = methodParameter.getContainingClass();
        FileFilterSetting fileFilterSetting = AnnotationUtil.getDeclaredAnnotation(methodParameter, FileFilterSetting.class);
        config = parseFile(fileFilterSetting.fileName());
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
     * @param object {@link Object} object which fields will be filtered
     * @throws FieldAccessException exception throws on {@link IllegalAccessException}
     */
    @Override
    public void filter(Object object, ServerHttpRequest request) throws FieldAccessException {
        SessionUtil sessionUtil = new SessionUtil(request);

        if (object != null && config != null) {
            for (FileConfig.Controller controller : config.getControllers()) {
                if (controllerClass.getName().equalsIgnoreCase(controller.getClassName())) {
                    controller.getStrategies().forEach(strategy -> {
                        if (sessionUtil.isSessionPropertyExists(sessionUtil.getSession(), strategy.getAttributeName(), strategy.getAttributeValue())) {
                            FieldFilterProcessor processor = new FieldFilterProcessor(AnnotationUtil.getStrategyFields(strategy));
                            processor.filterFields(object);
                        }
                    });
                }
            }
        }
    }


}
