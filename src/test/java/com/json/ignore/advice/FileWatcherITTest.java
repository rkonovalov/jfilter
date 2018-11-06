package com.json.ignore.advice;

import com.json.ignore.mock.config.WSConfigurationEnabled;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import static com.json.ignore.filter.file.FileFilter.resourceFile;
import static org.junit.Assert.*;

@ContextConfiguration(classes = WSConfigurationEnabled.class)
@RunWith(SpringRunner.class)
@WebAppConfiguration("src/main/resources")
public class FileWatcherITTest implements FileWatcherEvent {
    private File file;

    @Autowired
    private FileWatcher fileWatcher;


    @Before
    public void init() {
        assertNotNull(fileWatcher);
        file = resourceFile("config.xml");
        assertNotNull(file);

        fileWatcher.add(file, this);
    }

    @Test
    public void testAdd() {
        boolean result = fileWatcher.add(file, this);
        assertTrue(result);
    }

    @Test
    public void testAddTwice() {
        boolean addedOne = fileWatcher.add(file, this);
        boolean addedTwo = fileWatcher.add(file, this);
        assertTrue(addedOne && addedTwo);
    }

    @Test
    public void testUnExistFile() {
        boolean result = fileWatcher.add(resourceFile("unexist_config.xml"), this);
        assertFalse(result);
    }

    @Test
    public void testFileIsModifiedFalse() {
        boolean result = fileWatcher.fileIsModified(file);
        assertFalse(result);
    }

    @Test
    public void testFileIsModifiedTrue() throws IOException {
        Files.write(file.toPath(), " ".getBytes(), StandardOpenOption.APPEND);
        assertTrue(fileWatcher.fileIsModified(file));
    }

    @After
    public void testFinalize() {
        try {
            assertNotNull(fileWatcher);
        } finally {
            fileWatcher = null;
            System.gc();
            assertNull(fileWatcher);
        }
    }

    @Override
    public void onEvent(File file) {
        System.out.println("File modified: " + file);
    }
}
