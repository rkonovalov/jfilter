package com.json.ignore.filter.file;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.json.ignore.FieldAccessException;
import com.json.ignore.filter.BaseFilter;
import com.json.ignore.filter.field.FieldFilterProcessor;
import com.json.ignore.request.RequestSession;
import org.springframework.core.MethodParameter;
import org.springframework.http.server.ServerHttpRequest;

import java.io.File;
import java.io.IOException;
import java.net.URL;

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
        return load(resourceFile(fileName));
    }

    /**
     * Convert cml file to Class
     * <p>
     * Deserialize xml file to Class
     * @param file {@link File} file from resource name if file exist
     * @return {@link Object} returns instantiated object type of specified class
     * @throws FieldAccessException {@link FieldAccessException} when caught {@link IOException}
     */
    private FileConfig load(File file) throws FieldAccessException {
        try {
            return file != null ? new XmlMapper().readValue(file, FileConfig.class) : null;
        } catch (IOException e) {
            throw new FieldAccessException(e);
        }
    }

    /**
     * Get file name from resource name
     * <p>
     * Returns local file name of resource
     * <p>
     * Example: resource name config.xml, return local file .../resources/config.xml
     * @param resourceName {@link String} resource name
     * @return {@link String} local file name, else null
     */
    private String getFileName(String resourceName) {
        if (resourceName != null) {
            ClassLoader classLoader = FileFilter.class.getClassLoader();
            URL url = classLoader.getResource(resourceName);
            return url != null ? url.getFile() : null;
        }
        return null;
    }

    /**
     * Get file from resource
     * <p>
     * Returns {@link File} file from resource name if file exist
     * @param resourceName {@link String} resource name
     * @return {@link File} if file exists, else null
     */
    private File resourceFile(String resourceName) {
        String fileName = getFileName(resourceName);
        return fileName != null ? new File(fileName) : null;
    }

    /**
     * Attempt to retrieve all FieldFilterSetting annotations from method
     *
     * @param methodParameter {@link MethodParameter} method parameter
     */
    @Override
    protected void setConfig(MethodParameter methodParameter) {
        controllerClass = methodParameter.getContainingClass();
        FileFilterSetting fileFilterSetting = getRequestMethodParameter().getDeclaredAnnotation(FileFilterSetting.class);
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
        RequestSession requestSession = new RequestSession(request);

        if (object != null && config != null) {
            for (FileConfig.Controller controller : config.getControllers()) {
                if (controllerClass.getName().equalsIgnoreCase(controller.getClassName())) {
                    controller.getStrategies().forEach(strategy -> {
                        if (requestSession.isSessionPropertyExists(requestSession.getSession(), strategy.getAttributeName(), strategy.getAttributeValue())) {
                            FieldFilterProcessor processor = new FieldFilterProcessor(strategy.getStrategyFields());
                            processor.filterFields(object);
                        }
                    });
                }
            }
        }
    }


}
