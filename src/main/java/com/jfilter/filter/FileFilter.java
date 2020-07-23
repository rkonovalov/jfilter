package com.jfilter.filter;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.jfilter.FilterException;
import com.jfilter.components.FileWatcher;
import com.jfilter.request.RequestSession;
import org.springframework.core.MethodParameter;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * This class used for filtration of object's fields based on xml file configuration
 */
public class FileFilter extends BaseFilter {

    private FileConfig config;
    private Class<?> controllerClass;
    private File file;

    /**
     * Creates a new instance of the {@link FileFilter} class.
     *
     * @param methodParameter {@link MethodParameter} method
     */
    public FileFilter(MethodParameter methodParameter) {
        super(methodParameter);
        setConfig(methodParameter);
    }

    public void setFileWatcher(FileWatcher fileWatcher) {
        if (fileWatcher != null)
            fileWatcher.add(file, f -> config = load(file));
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
     *
     * @param file {@link File} file from resource name if file exist
     * @return {@link Object} returns instantiated object type of specified class
     */
    private FileConfig load(File file) {
        this.file = file;
        try {
            if (file != null) {
                String extension = fileExtension(file);
                switch (extension) {
                    case "yml":
                    case "yaml":
                        return new YAMLMapper().readValue(file, FileConfig.class);
                    case "xml":
                    default:
                        return new XmlMapper().readValue(file, FileConfig.class);
                }
            } else
                return this.config;
        } catch (IOException e) {
            throw new FilterException(e);
        }
    }

    /**
     * Get file extension
     *
     * @param file instance of {@link File}
     * @return return file extension otherwise return empty string
     */
    public String fileExtension(File file) {
        String fileName = file.getName();
        int index = fileName.lastIndexOf(".");
        return index > 0 ? fileName.substring(index + 1).toLowerCase() : "";
    }

    /**
     * Get file name from resource name
     * <p>
     * Returns local file name of resource
     * <p>
     * Example: resource name config.xml, return local file .../resources/config.xml
     *
     * @param resourceName {@link String} resource name
     * @return {@link String} local file name, else null
     */
    public static String getFileName(String resourceName) {
        if (!resourceName.isEmpty()) {
            ClassLoader classLoader = FileFilter.class.getClassLoader();
            URL url = classLoader.getResource(resourceName);
            return url != null ? url.getFile() : resourceName;
        }
        return "";
    }

    /**
     * Get file from resource
     * <p>
     * Returns {@link File} file from resource name if file exist
     *
     * @param resourceName {@link String} resource name
     * @return {@link File} if file exists, else null
     */
    public static File resourceFile(String resourceName) {
        String fileName = getFileName(resourceName);
        File file = new File(fileName);
        return file.exists() ? file : null;
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
        if (fileFilterSetting != null)
            config = parseFile(fileFilterSetting.fileName());
    }

    @Override
    public FilterFields getFields(Object object, RequestSession request) {
        FilterFields result = new FilterFields();
        if (config != null) {
            for (FileConfig.Controller controller : config.getControllers()) {
                if (controllerClass.getName().equalsIgnoreCase(controller.getClassName())) {
                    controller.getStrategies().forEach(strategy -> {
                        if (request.isSessionPropertyExists(strategy.getAttributeName(), strategy.getAttributeValue())) {
                            strategy.appendStrategyFields(result);
                        }
                    });
                }
            }
        }
        return result;
    }

    /*public static void main(String[] args) throws IOException {
        File file = new File("/Users/ruslan/IdeaProjects/jfilter/src/test/resources/config.xml");

        FileConfig fileConfig = new XmlMapper().readValue(file, FileConfig.class);

        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        objectMapper.writeValue(new File("/Users/ruslan/IdeaProjects/jfilter/src/test/resources/config.yaml"), fileConfig);


        System.out.println(fileConfig);
    }*/
}
