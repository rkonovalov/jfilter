package com.json.ignore.advice;

import com.json.ignore.mock.config.WSConfigurationEnabled;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.json.ignore.filter.file.FileFilter.resourceFile;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.junit.Assert.*;

@ContextConfiguration(classes = WSConfigurationEnabled.class)
@RunWith(SpringRunner.class)
@WebAppConfiguration("src/main/resources")
public class FileWatcherITTest {
    private File file;
    private AtomicBoolean modified;

    @Autowired
    private FileWatcher fileWatcher;


    @Before
    public void init() {
        modified = new AtomicBoolean(false);
        assertNotNull(fileWatcher);

        file = resourceFile("config.xml");
        assertNotNull(file);

        fileWatcher.add(file, (f) -> modified.set(true));
    }

    @Test
    public void testWWWFileIsModifiedTrue() throws IOException {
        Files.write(file.toPath(), " ".getBytes(), StandardOpenOption.APPEND);
        await().atMost(5, SECONDS).untilTrue(modified);
        assertTrue(modified.get());
    }

    @Test
    public void testAdd() {
        boolean result = fileWatcher.add(file, (f) -> modified.set(true));
        assertTrue(result);
    }

    @Test
    public void testAddTwice() {
        boolean addedOne = fileWatcher.add(file, (f) -> modified.set(true));
        boolean addedTwo = fileWatcher.add(file, (f) -> modified.set(true));
        assertTrue(addedOne && addedTwo);
    }

    @Test
    public void testUnExistFile() {
        boolean result = fileWatcher.add(resourceFile("unexist_config.xml"), (f) -> modified.set(true));
        assertFalse(result);
    }

    @Test
    public void testFileIsModifiedFalse() {
        boolean result = fileWatcher.fileIsModified(file);
        assertFalse(result);
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
}
