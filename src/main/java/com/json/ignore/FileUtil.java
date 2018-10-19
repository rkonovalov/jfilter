
/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.json.ignore;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.json.ignore.filter.file.FileConfig;

import java.io.*;
import java.net.URL;

/**
 * @author Ruslan {@literal <rkonovalov86@gmail.com>}
 * @version 1.0
 */

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
            if (url != null) {
                String pathName = url.getFile();
                if (!pathName.isEmpty()) {
                    return new File(pathName);
                }
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
