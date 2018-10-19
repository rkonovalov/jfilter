package com.json.ignore.filter.file;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.json.ignore.FieldAccessException;
import com.json.ignore.FieldClassNotFoundException;
import com.json.ignore.FileIOException;
import com.json.ignore.filter.AnnotationUtil;
import com.json.ignore.filter.BaseFilter;
import com.json.ignore.filter.field.FieldFilterProcessor;
import org.springframework.core.MethodParameter;
import org.springframework.http.server.ServerHttpRequest;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URL;
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

    private String inputStreamToString(InputStream is) throws FileIOException {
        if (is != null) {
            StringBuilder sb = new StringBuilder();
            String line;
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            try {
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                br.close();
            } catch (IOException e) {
                throw new FileIOException(e);
            }
            return sb.toString();
        } else
            return null;
    }

    private FileConfig parseFile(File file) {
        if (file != null) {
            try {
                XmlMapper xmlMapper = new XmlMapper();
                xmlMapper.setDefaultUseWrapper(false);
                String xml = inputStreamToString(new FileInputStream(file));
                if (xml != null) {
                    return xmlMapper.readValue(xml, FileConfig.class);
                }
            } catch (IOException e) {
                throw new FileIOException(e);
            }
        }
        return null;
    }

    public FileConfig parseFile(String fileName) throws FileIOException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL url = classLoader.getResource(fileName);
        if (url != null) {
            String pathName = url.getFile();
            if (pathName != null) {
                File file = new File(pathName);
                return parseFile(file);
            }
        }
        return null;
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

    private Class getClassByName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new FieldClassNotFoundException(e);
        }
    }

    private Map<Class, List<String>> getStrategyFields(FileConfig.Strategy strategy) {
        Map<Class, List<String>> fields = new HashMap<>();

        if (strategy != null) {
            strategy.getFilters().forEach(filter -> {
                Class clazz = getClassByName(filter.getClassName());
                List<String> items;

                if (fields.containsKey(clazz)) {
                    items = fields.get(clazz);
                } else
                    items = new ArrayList<>();

                filter.getFields().forEach(field -> items.add(field.getName()));

                fields.put(clazz, items);
            });
        }
        return fields;
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
        if (object != null && fileConfig != null) {
            for (FileConfig.Controller controller : fileConfig.getControllers()) {
                if (controllerClass.getName().equals(controller.getClassName())) {

                    controller.getStrategies().forEach(strategy -> {
                        if (isSessionPropertyExists(strategy.getAttributeName(), strategy.getAttributeValue())) {
                            FieldFilterProcessor processor = new FieldFilterProcessor(getStrategyFields(strategy));
                            processor.filterFields(object);
                        }
                    });
                }
            }
        }
    }


}
