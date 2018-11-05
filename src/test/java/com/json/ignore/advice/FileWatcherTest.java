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

@ContextConfiguration(classes = WSConfigurationEnabled.class)
@RunWith(SpringRunner.class)
@WebAppConfiguration("src/main/resources")
public class FileWatcherTest {
    @Autowired
    private FileWatcher fileWatcher;

    @Before
    public void init() {
        Assert.assertNotNull(fileWatcher);
    }

    @Test
    public void testAdd() {
        boolean result = fileWatcher.add(FileFilter.resourceFile("config.xml"), f -> System.out.println(f.getAbsoluteFile()));
        Assert.assertTrue(result);
    }

    @Test
    public void testAddTwice() {
        fileWatcher.add(FileFilter.resourceFile("config.xml"), f -> System.out.println(f.getAbsoluteFile()));

        boolean result = fileWatcher.add(FileFilter.resourceFile("config.xml"), f -> System.out.println(f.getAbsoluteFile()));
        Assert.assertTrue(result);
    }
}
