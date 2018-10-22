package com.json.ignore.util;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.json.ignore.FieldAccessException;
import java.io.*;
import java.net.URL;

/**
 * File util class
 * <p>
 * This is util class used to help working with files
 */
public final class FileUtil {



    /**
     * Gets class by name
     * <P>
     * Try to get class by it full name. If class couldn't be found, returns null
     * @param className {@link String} class name. Example: java.io.File
     * @return {@link Class} return class, else null
     */
    public static Class getClassByName(String className) {
        if (className != null && !className.isEmpty()) {
            try {
                return Class.forName(className);
            } catch (ClassNotFoundException e) {
                return null;
            }
        } else
            return null;
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
    private static String getFileName(String resourceName) {
        if (resourceName != null) {
            ClassLoader classLoader = FileUtil.class.getClassLoader();
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
    public static File resourceFile(String resourceName) {
        String fileName = getFileName(resourceName);
        return fileName != null ? new File(fileName) : null;
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
    public static <T> T xmlFileToClass(File file, Class<T> clazz) throws FieldAccessException {
        try {
            return file != null ? new XmlMapper().readValue(file, clazz) : null;
        } catch (IOException e) {
            throw new FieldAccessException(e);
        }
    }
}
