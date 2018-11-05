package com.json.ignore.advice;

import com.json.ignore.filter.file.FileFilter;
import com.json.ignore.mock.config.WSConfigurationEnabled;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import java.io.File;
import java.util.Date;

import static com.json.ignore.filter.file.FileFilter.resourceFile;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@ContextConfiguration(classes = WSConfigurationEnabled.class)
@RunWith(SpringRunner.class)
@WebAppConfiguration("src/main/resources")
public class FileWatcherITTest {
    private File file;
    @Autowired
    private FileWatcher fileWatcher;

    @Before
    public void init() {
        Assert.assertNotNull(fileWatcher);
        file = resourceFile("config.xml");
        assertNotNull(file);
    }

    @Test
    public void testAdd() {
        boolean result = fileWatcher.add(file, f -> System.out.println(f.getAbsoluteFile()));
        assertTrue(result);
    }

    @Test
    public void testAddTwice() {
        fileWatcher.add(file, f -> System.out.println(f.getAbsoluteFile()));

        boolean result = fileWatcher.add(file, f -> System.out.println(f.getAbsoluteFile()));
        assertTrue(result);
    }

    @Test
    public void testUnExistFile() {
        boolean result = fileWatcher.add(resourceFile("unexist_config.xml"), f -> System.out.println(f.getAbsoluteFile()));
        assertFalse(result);
    }

    @Test
    public void testFileIsModifiedFalse() {
        boolean result = fileWatcher.fileIsModified(file);
        assertFalse(result);
    }

    @Test
    public void testFileIsModifiedTrue() {
        boolean added = fileWatcher.add(file, f -> System.out.println(f.getAbsoluteFile()));
        assertTrue(added);

        boolean modified = file.setLastModified(new Date().getTime());
        assertTrue(modified);

        boolean result = fileWatcher.fileIsModified(file);
        assertTrue(result);
    }
}
