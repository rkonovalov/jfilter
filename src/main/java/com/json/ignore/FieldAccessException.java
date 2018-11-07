package com.json.ignore;

import com.sun.istack.internal.Nullable;

/**
 * Exception will throw on {@link IllegalAccessException}
 */
public class FieldAccessException extends RuntimeException {

    public FieldAccessException(Throwable cause) {
       super(cause);
    }

    public FieldAccessException(String msg) {
        super(msg);
    }

    public FieldAccessException(@Nullable String msg, @Nullable Throwable cause) {
        super(msg, cause);
    }
}
