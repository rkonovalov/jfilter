package com.jfilter.components;

import com.jfilter.mock.config.WSConfigurationHelper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.util.Date;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertTrue;

@Component
public class FileWatcherOverflowITest {
    private FileWatcher fileWatcher;

    @Autowired
    public void setFileWatcher(FileWatcher fileWatcher) {
        this.fileWatcher = fileWatcher;
    }

    @Before
    public void init() throws Exception {
        WSConfigurationHelper.instance(WSConfigurationHelper.Instance.FILTER_ENABLED, this);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test
    @Ignore
    public void testOverflowKey() throws IOException {
        int fileCount = 200;
        Path directory = Files.createTempDirectory("watch-service-overflow");

        directory.register(
                fileWatcher.getWatcher(),
                StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_DELETE,
                StandardWatchEventKinds.ENTRY_MODIFY);
        final Path p = directory.resolve(Paths.get("TEMPORARY_FILE"));
        for (int i = 0; i < fileCount; i++) {
            File createdFile = Files.createFile(p).toFile();
            createdFile.setLastModified(new Date().getTime() + 60);
            Files.delete(p);
        }
        Files.delete(directory);

        await().atMost(60, SECONDS).until(() -> fileWatcher.isOverflowed());
        assertTrue(fileWatcher.isOverflowed());
    }
}
