package com.json.ignore.advice;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

/**
 * File watcher class
 *
 * <p>This class used for watching on file modified event
 * Also class uses Spring Scheduling mechanism for periodically checking files
 */
@EnableScheduling
@Controller
public final class FileWatcher {

    private static final Long FILE_MODIFY_THRESHOLD = 1000L;
    private static final String FILE_MODIFY_DELAY = "2000";

    private WatchService watcher;
    private Map<WatchKey, Path> watchKeys;
    private Map<File, FileRecord> fileRecords;
    private ThreadPoolTaskScheduler scheduler;

    /**
     * File watcher record
     */
    public static class FileRecord {
        private final File file;
        private final FileWatcherEvent event;
        private Long lastModified;

        /**
         * Creates a new instance of the {@link FileRecord} class.
         *
         * @param file  file
         * @param event event which occurs on file modification
         */
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

        /**
         * On file modify occur
         *
         * @return {@link Boolean} true if event not null, otherwise false
         */
        public boolean onEvent() {
            if (event != null) {
                event.onEvent(file);
                return true;
            } else
                return false;
        }
    }

    /**
     * Creates a new instance of the {@link FileWatcher} class.
     *
     * @throws IOException If an I/O error occurs
     */
    public FileWatcher() throws IOException {
        watcher = FileSystems.getDefault().newWatchService();
        watchKeys = new HashMap<>();
        fileRecords = new HashMap<>();
    }

    public FileWatcher setScheduler(ThreadPoolTaskScheduler scheduler) {
        this.scheduler = scheduler;
        return this;
    }

    /**
     * Add new file watcher
     *
     * @param file  file
     * @param event event which occurs on file modification
     * @return {@link Boolean} true if watcher is added, otherwise false
     */
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

    /**
     * Check if file is modified
     *
     * <p>Method compares file lastModified value and lastModified which added on watcher creation
     * If difference between this two values is greater than FILE_MODIFY_THRESHOLD then method returns true,
     * otherwise false
     *
     * <p>FILE_MODIFY_THRESHOLD used because when file is modifying it modifies twice:
     * <ul>
     * <li>When file content modified</li>
     * <li>When file modification information changed</li>
     * </ul>
     * Therefore we need to filter this duplicated modify events.
     *
     * @param file file
     * @return true if file modified, otherwise false
     */
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

    /**
     * Process all modify events
     *
     * @throws InterruptedException if interrupted while waiting
     */
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

    /**
     * Process modify events by schedule
     *
     * <p>FILE_MODIFY_DELAY used for set schedule repeat delay
     *
     * @throws InterruptedException if interrupted while waiting
     */
    @Scheduled(fixedDelayString = FILE_MODIFY_DELAY)
    protected void scheduleModifiedFiles() throws InterruptedException {
        try {
            processModifiedFiles();
        } catch (ClosedWatchServiceException e) {
            scheduler.shutdown();
        }
    }

    /**
     * Finalizing watcher
     *
     * @throws Throwable exception
     */
    @Override
    protected void finalize() throws Throwable {
        try {
            watcher.close();
        } finally {
            super.finalize();
        }
    }
}
