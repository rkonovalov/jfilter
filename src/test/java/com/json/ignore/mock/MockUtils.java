package com.json.ignore.mock;

import com.json.ignore.filter.file.FileFilter;
import org.awaitility.core.ConditionTimeoutException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static java.nio.file.StandardOpenOption.WRITE;
import static org.awaitility.Awaitility.await;

public class MockUtils {

    public static boolean sleep(Integer timeout) {
        try {
            await().atMost(timeout, TimeUnit.SECONDS)
                    .untilTrue(new AtomicBoolean(false));
            return true;
        } catch (ConditionTimeoutException e) {
            return false;
        }
    }

    public static boolean fileCopy(String sourceName, String destinationName) {
        File sourceFile = FileFilter.resourceFile(sourceName);
        File destinationFile = FileFilter.resourceFile(destinationName);
        if (sourceFile == null || destinationFile == null)
            return false;

        if (sourceFile.exists() && destinationFile.exists()) {
            try {
                Files.copy(sourceFile.toPath(), destinationFile.toPath(), REPLACE_EXISTING);
            } catch (IOException e) {
                return false;
            }
            return true;
        }
        return false;
    }

    public static boolean fileWrite(File file, String content) {
        try {
            if (file != null && file.exists()) {
                Files.write(file.toPath(), content.getBytes(), WRITE);
                return true;
            } else
                return false;
        } catch (IOException e) {
            return false;
        }
    }

}
