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
import java.util.Date;
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
    private AtomicBoolean changed;

    @Autowired
    private FileWatcher fileWatcher;


    @Before
    public void init() {
        Assert.assertNotNull(fileWatcher);
        file = resourceFile("config.xml");
        assertNotNull(file);

        changed = new AtomicBoolean(false);
        fileWatcher.add(file, this);
    }

    @Test
    public void testAdd() {
        boolean result = fileWatcher.add(file, this);
        assertTrue(result);
    }

    @Test
    public void testAddTwice() {
        boolean addedOne =  fileWatcher.add(file, this);
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

    public void onEvent(File file) {
        System.out.println("Changed: " + file.getAbsolutePath());
        changed.set(true);
    }
}
