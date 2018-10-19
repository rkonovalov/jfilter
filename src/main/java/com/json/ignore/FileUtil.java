package com.json.ignore;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.*;
import java.net.URL;

public class FileUtil {

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

    public static String getFileName(String resourceName) {
        if (resourceName != null) {
            ClassLoader classLoader = FileUtil.class.getClassLoader();
            URL url = classLoader.getResource(resourceName);
            return url != null ? url.getFile() : null;
        }
        return null;
    }

    public static File resourceFile(String resourceName) {
        String fileName = getFileName(resourceName);
        return fileName != null ? new File(fileName) : null;
    }

    public static FileInputStream fileToInputStream(File file) {
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException | NullPointerException e) {
            return null;
        }
    }

    public static <T> T xmlFileToClass(File file, Class<T> clazz) {
        if (file == null)
            return null;

        try {
            return new XmlMapper().readValue(file, clazz);
        } catch (IOException e) {
            return null;
        }
    }
}
