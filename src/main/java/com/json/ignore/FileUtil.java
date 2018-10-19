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

    public static File resourceFile(String fileName) {
        if (fileName != null) {
            ClassLoader classLoader = FileUtil.class.getClassLoader();
            URL url = classLoader.getResource(fileName);

            try {
                String pathName = url.getFile();
                if(pathName != null) {
                    return new File(pathName);
                }

            } catch (NullPointerException e) {
                return null;
            }

        }
        return null;
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
        if (inputStream != null) {
            return inputStreamToString(inputStream);
        } else
            return null;
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
                if (xml != null) {
                    return xmlMapper.readValue(xml, clazz);
                }
            } catch (IOException e) {
                return null;
            }
        }
        return null;
    }
}
