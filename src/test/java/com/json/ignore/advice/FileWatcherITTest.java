package com.json.ignore.advice;

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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.json.ignore.filter.file.FileFilter.resourceFile;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@ContextConfiguration(classes = WSConfigurationEnabled.class)
@RunWith(SpringRunner.class)
@WebAppConfiguration("src/main/resources")
public class FileWatcherITTest implements FileWatcherEvent {
    private File file;
    private AtomicBoolean changed = new AtomicBoolean(false);

    @Autowired
    private FileWatcher fileWatcher;


    @Before
    public void init() {
        Assert.assertNotNull(fileWatcher);
        file = resourceFile("config.xml");
        assertNotNull(file);

        fileWatcher.add(file, this);
    }

    @Test
    public void testAdd() {
        boolean result = fileWatcher.add(file, f -> changed.set(true));
        assertTrue(result);
    }

    @Test
    public void testAddTwice() {
        boolean addedOne = fileWatcher.add(file, f -> changed.set(true));
        boolean addedTwo = fileWatcher.add(file, f -> changed.set(true));
        assertTrue(addedOne && addedTwo);
    }

    @Test
    public void testUnExistFile() {
        boolean result = fileWatcher.add(resourceFile("unexist_config.xml"), f -> changed.set(true));
        assertFalse(result);
    }

    @Test
    public void testFileIsModifiedFalse() {
        boolean result = fileWatcher.fileIsModified(file);
        assertFalse(result);
    }

    @Test
    public void testFileIsModifiedTrue() throws InterruptedException, IOException {
        Thread.sleep(2000);
        Files.write(file.toPath(), " ".getBytes(), StandardOpenOption.APPEND);
        assertTrue(fileWatcher.fileIsModified(file));
    }

    @Override
    public void onEvent(File file) {
        changed.set(true);
        System.out.println("changed");
        System.out.println(changed.get());
    }
}
