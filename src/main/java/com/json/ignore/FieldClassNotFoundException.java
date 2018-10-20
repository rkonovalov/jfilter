package com.json.ignore;

/**
 * Exception will throw on {@link IllegalAccessException}
 */

public class FieldClassNotFoundException extends RuntimeException {
    public FieldClassNotFoundException(Throwable cause) {
        super(cause);
    }
}
