package com.json.ignore;

/**
 * Exception will throw on {@link IllegalAccessException}
 */

public class FieldAccessException extends RuntimeException {
    public FieldAccessException(Throwable cause) {
        super(cause);
    }
}
