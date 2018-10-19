package com.json.ignore.filter.file;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.json.ignore.FieldAccessException;
import com.json.ignore.FileIOException;
import com.json.ignore.filter.AnnotationUtil;
import com.json.ignore.filter.BaseFilter;
import com.json.ignore.filter.field.FieldFilterProcessor;
import org.springframework.core.MethodParameter;
import org.springframework.http.server.ServerHttpRequest;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;

public class FileFilter extends BaseFilter {
    private FileFilterSetting config;
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

    private static String inputStreamToString(InputStream is) throws FileIOException {
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
    }

    private FileConfig parseFile(File file) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.setDefaultUseWrapper(false);
        String xml = inputStreamToString(new FileInputStream(file));
        return xmlMapper.readValue(xml, FileConfig.class);
    }

    public FileConfig parseFile(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        try {
            String pathName = Objects.requireNonNull(classLoader.getResource(fileName)).getFile();
            File file = new File(pathName);
            return parseFile(file);
        } catch (IOException e) {

            return null;
        }
    }

    /**
     * Attempt to retrieve all FieldFilterSetting annotations from method
     *
     * @param methodParameter {@link MethodParameter} method parameter
     */
    @Override
    public void setConfig(MethodParameter methodParameter) {
        controllerClass = methodParameter.getContainingClass();
        config = AnnotationUtil.getDeclaredAnnotation(methodParameter.getMethod(), FileFilterSetting.class);
        fileConfig = parseFile(config.fileName());
    }

    private Class getClassByName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    private Map<Class, List<String>> getStrategyFields(FileConfig.Strategy strategy) {
        Map<Class, List<String>> fields = new HashMap<>();

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
        return fields;
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
