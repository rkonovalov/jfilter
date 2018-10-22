package com.json.ignore.util;

import org.junit.Test;
import java.io.File;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class FileUtilTest {
    private static final String EXISTED_FILE = "config.xml";
    private static final String UN_EXISTED_FILE = "unexisted_config.xml";

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
}
