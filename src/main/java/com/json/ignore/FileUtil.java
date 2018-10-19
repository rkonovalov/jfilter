package com.json.ignore;

import com.fasterxml.jackson.core.JsonProcessingException;
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

    public static String fileToString(File file) {
        FileInputStream inputStream = fileToInputStream(file);
        return inputStream != null ? inputStreamToString(inputStream) : null;
    }

    public static String inputStreamToString(File file) {
        return inputStreamToString(fileToInputStream(file));
    }

    public static String inputStreamToString(InputStream is) {
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
                return null;
            }
            return sb.toString();
        } else
            return null;
    }

    public static <T> T xmlFileToClass(File file, Class<T> clazz) {
        if (file != null) {
            try {
                XmlMapper xmlMapper = new XmlMapper();
                xmlMapper.setDefaultUseWrapper(false);
                String xml = FileUtil.inputStreamToString(file);
                return xml != null ? xmlMapper.readValue(xml, clazz) : null;
            } catch (IOException e) {
                return null;
            }
        }
        return null;
    }
}
