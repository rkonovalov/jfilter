package com.json.ignore.advice;


import com.json.ignore.mock.config.WSConfiguration;
import org.awaitility.core.ConditionTimeoutException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.json.ignore.filter.file.FileFilter.resourceFile;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@Component
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

        boolean add = fileWatcher.add(file, (f) -> modified.set(true));
        assertTrue(add);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test
    public void testFileIsModifiedTrue() {
        file.setLastModified(new Date().getTime() + 5000);
        await().atMost(5, SECONDS).until(() -> modified.get());

        assertTrue(modified.get());
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test
    public void testFileIsModifiedFalse() {
        /*
           Try to modify file, result should be modified
         */
        file.setLastModified(new Date().getTime() + 1000);
        await().atMost(5, SECONDS).until(() -> modified.get());

        assertTrue(modified.get());

        /*
           Try to modify file, result should be NOT modified, because lastModify date lass than the file modify date
         */
        modified.set(false);
        file.setLastModified(new Date().getTime() - 1000);
        try {
            await().atMost(5, SECONDS).until(() -> modified.get());
        } catch (ConditionTimeoutException e) {
            modified.set(false);
        }

        assertFalse(modified.get());
    }
}
