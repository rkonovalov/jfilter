package com.json.ignore.advice;

import java.io.File;

/**
 * The event that occurs when file is modified
 */
public interface FileWatcherEvent {

    /**
     * Event occurs if file modified
     *
     * @param file modified file
     */
    void onEvent(File file);
}
