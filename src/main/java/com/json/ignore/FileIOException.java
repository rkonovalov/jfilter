package com.json.ignore;

/**
 * Exception will throw on {@link java.io.IOException}
 */

public class FileIOException extends RuntimeException {
    public FileIOException(Throwable cause) {
        super(cause);
    }
}
