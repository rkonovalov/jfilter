package com.json.ignore.filter.file;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.json.ignore.FieldAccessException;
import com.json.ignore.FieldClassNotFoundException;
import com.json.ignore.FileIOException;
import com.json.ignore.FileUtil;
import com.json.ignore.filter.AnnotationUtil;
import com.json.ignore.filter.BaseFilter;
import com.json.ignore.filter.field.FieldFilterProcessor;
import org.springframework.core.MethodParameter;
import org.springframework.http.server.ServerHttpRequest;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;

/**
 * This class used for filtration of object's fields based on xml file configuration
 */
public class FileFilter extends BaseFilter {
    private FileConfig fileConfig;
    private Class controllerClass;

    public FileFilter(ServerHttpRequest serverHttpRequest, MethodParameter methodParameter) {
        super(serverHttpRequest);
        setConfig(methodParameter);
    }

    public FileFilter(HttpSession session, MethodParameter methodParameter) {
        super(session);
        setConfig(methodParameter);
    }

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




    public Class getControllerClass() {
        return controllerClass;
    }

    public FileFilter setControllerClass(Class controllerClass) {
        this.controllerClass = controllerClass;
        return this;
    }

    @Override
    public void filter(Object object) throws FieldAccessException {
        if (object != null) {
            if (fileConfig.getControllers() != null)
                for (FileConfig.Controller controller : fileConfig.getControllers()) {
                    if (controllerClass.getName().equals(controller.getClassName())) {

                        if (controller.getStrategies() != null)
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
