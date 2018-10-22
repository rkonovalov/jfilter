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
}
