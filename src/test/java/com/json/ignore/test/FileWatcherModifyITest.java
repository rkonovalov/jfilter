package com.json.ignore.test;

import com.json.ignore.advice.FileWatcher;
import com.json.ignore.mock.config.WSConfiguration;
import com.json.ignore.mock.config.WSConfigurationEnabled;
import org.awaitility.core.ConditionTimeoutException;
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
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class FileWatcherModifyITest {
    private FileWatcher fileWatcher;
    private AtomicBoolean modified;
    private File file;

    @Autowired
    public void setFileWatcher(FileWatcher fileWatcher) {
        this.fileWatcher = fileWatcher;
    }

    @Before
    public void init() throws Exception {
        WSConfiguration.instance(WSConfiguration.Instance.FILTER_ENABLED, this);

        modified = new AtomicBoolean(false);

        file = resourceFile("config.xml");
        assertNotNull(file);

        boolean add = fileWatcher.add(file, (f) -> {
            modified.set(true);
            System.out.println("modified-> " + modified.get());
        });
        assertTrue(add);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test
    public void testFileIsModifiedExternally() {
        System.out.println("modified-> " + modified.get());
        boolean result = file.setLastModified(new Date().getTime() + 5000);
        await().atMost(5, SECONDS).until(() -> modified.get());
        assertTrue(modified.get());
    }
}
