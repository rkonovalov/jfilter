package com.json.ignore;

/**
 * Exception will throw on {@link IllegalAccessException}
 */
public class FieldAccessException extends RuntimeException {

    private static final long serialVersionUID = -1580361832535921569L;

    public FieldAccessException(Throwable cause) {
        super(cause);
    }

}
