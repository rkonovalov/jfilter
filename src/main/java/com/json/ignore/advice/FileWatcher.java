package com.json.ignore.advice;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

@EnableScheduling
@Controller
public final class FileWatcher {

    private static final Long FILE_MODIFY_THRESHOLD = 1000L;
    private static final String FILE_MODIFY_DELAY = "2000";

    private WatchService watcher;
    private Map<WatchKey, Path> watchKeys;
    private Map<File, FileRecord> fileRecords;

    public static class FileRecord {
        private File file;
        private FileWatcherEvent event;
        private Long lastModified;

        public FileRecord(File file, FileWatcherEvent event) {
            this.file = file;
            this.event = event;
            this.lastModified = file.lastModified();
        }

        public Long getLastModified() {
            return lastModified;
        }

        public FileRecord setLastModified(Long lastModified) {
            this.lastModified = lastModified;
            return this;
        }

        public boolean onEvent() {
            if (event != null) {
                event.onEvent(file);
                return true;
            } else
                return false;
        }
    }

    public FileWatcher() throws IOException {
        watcher = FileSystems.getDefault().newWatchService();
        watchKeys = new HashMap<>();
        fileRecords = new HashMap<>();
    }

    public boolean add(File file, FileWatcherEvent event) {
        if (file == null || !file.exists())
            return false;

        if (!fileRecords.containsKey(file))
            fileRecords.put(file, new FileRecord(file, event));

        Path path = file.isDirectory() ? Paths.get(file.getPath()) : Paths.get(file.getParent());
        if (!watchKeys.containsValue(path)) {
            try {
                watchKeys.put(path.register(watcher, ENTRY_MODIFY), path);
                return true;
            } catch (IOException e) {
                return false;
            }
        } else
            return true;
    }

    public boolean fileIsModified(File file) {
        boolean result = false;
        if (fileRecords.containsKey(file)) {
            FileRecord record = fileRecords.get(file);
            Long lastModified = record.getLastModified();
            if (file.lastModified() - lastModified > FILE_MODIFY_THRESHOLD) {
                record.setLastModified(file.lastModified());
                result = true;
            }
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    private void processModifiedFiles() throws InterruptedException {
        WatchKey key = watcher.take();
        if (key != null) {
            for (WatchEvent<?> event : key.pollEvents()) {
                WatchEvent.Kind<?> kind = event.kind();

                if (kind == StandardWatchEventKinds.OVERFLOW)
                    continue;

                WatchEvent<Path> ev = (WatchEvent<Path>) event;
                String filename = String.format("%s%s%s", watchKeys.get(key).toString(),
                        File.separator, ev.context().toString());
                File file = new File(filename);

                if (fileIsModified(file))
                    fileRecords.get(file).onEvent();
            }
            key.reset();
        }
    }

    @Scheduled(fixedDelayString = FILE_MODIFY_DELAY)
    protected void waitFileModify() throws InterruptedException {
        processModifiedFiles();
    }
}
