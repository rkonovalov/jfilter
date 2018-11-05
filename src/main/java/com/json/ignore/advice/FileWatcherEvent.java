package com.json.ignore.advice;

import java.io.File;

public interface FileWatcherEvent {
    void onEvent(File file);
}
