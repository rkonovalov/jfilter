package com.json.ignore.filter;

import com.json.ignore.FieldAccessException;
import com.json.ignore.filter.file.FileConfig;
import org.junit.Test;
import java.io.File;
import java.io.FileInputStream;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class FileUtilTest {
    private static final String EXISTED_FILE = "config.xml";
    private static final String UN_EXISTED_FILE = "unexisted_config.xml";

    @Test
    public void testClassExists() {
        Class clazz = FileUtil.getClassByName("com.json.ignore.filter.FileUtilTest");
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
