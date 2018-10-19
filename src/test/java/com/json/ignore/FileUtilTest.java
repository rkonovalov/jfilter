
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

import com.json.ignore.filter.file.FileConfig;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * @author Ruslan {@literal <rkonovalov86@gmail.com>}
 * @version 1.0
 */

public class FileUtilTest {
    private static final String EXISTED_FILE = "config.xml";
    private static final String UN_EXISTED_FILE = "unexisted_config.xml";

    @Test
    public void testClassExists() {
        Class clazz = FileUtil.getClassByName("com.json.ignore.FileUtilTest");
        assertNotNull(clazz);
    }

    @Test
    public void testClassNotExists() {
        Class clazz = FileUtil.getClassByName("com.json.ignore.NotExistedClass");
        assertNull(clazz);
    }

    @Test
    public void testClassEmpty() {
        Class clazz = FileUtil.getClassByName("");
        assertNull(clazz);
    }

    @Test
    public void testClassNull() {
        Class clazz = FileUtil.getClassByName(null);
        assertNull(clazz);
    }

    @Test
    public void testGetFileNameExist() {
        String fileName = FileUtil.getFileName(EXISTED_FILE);
        assertNotNull(fileName);
    }

    @Test
    public void testGetFileNameNotExist() {
        String fileName = FileUtil.getFileName(UN_EXISTED_FILE);
        assertNull(fileName);
    }

    @Test
    public void testGetFileNameNull() {
        String fileName = FileUtil.getFileName(null);
        assertNull(fileName);
    }


    @Test
    public void testResourceFileNotNull() {
        File file = FileUtil.resourceFile(EXISTED_FILE);
        assertNotNull(file);
    }

    @Test
    public void testResourceFileUnExistFile() {
        File file = FileUtil.resourceFile(UN_EXISTED_FILE);
        assertNull(file);
    }

    @Test
    public void testResourceFileNull() {
        File file = FileUtil.resourceFile(null);
        assertNull(file);
    }

    @Test
    public void testFileToInputStreamNotNull() {
        File file = FileUtil.resourceFile(EXISTED_FILE);
        FileInputStream inputStream = FileUtil.fileToInputStream(file);
        assertNotNull(inputStream);
    }

    @Test
    public void testFileToInputStreamNull() {
        FileInputStream inputStream = FileUtil.fileToInputStream(null);
        assertNull(inputStream);
    }

    @Test
    public void testXmlFileToClassNotNull() {
        File file = FileUtil.resourceFile(EXISTED_FILE);
        FileConfig config = FileUtil.xmlFileToClass(file, FileConfig.class);
        assertNotNull(config);
    }

    @Test
    public void testXmlFileToClassNull() {
        FileConfig config = FileUtil.xmlFileToClass(null, FileConfig.class);
        assertNull(config);
    }

    @Test(expected = FieldAccessException.class)
    public void testXmlFileToClassIncorrectClass() {
        File file = FileUtil.resourceFile(EXISTED_FILE);
        FileUtilTest config = FileUtil.xmlFileToClass(file, FileUtilTest.class);
        assertNull(config);
    }

    @Test(expected = FieldAccessException.class)
    public void testXmlFileToClassBadFile() {
        File file = FileUtil.resourceFile("bad_config.xml");
        FileUtilTest config = FileUtil.xmlFileToClass(file, FileUtilTest.class);
        assertNull(config);
    }


}
