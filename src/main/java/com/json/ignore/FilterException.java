package com.json.ignore;

/**
 * Exception will throw on {@link IllegalAccessException}
 */
public class FilterException extends RuntimeException {

    private static final long serialVersionUID = -1580361832535921569L;

    public FilterException(Throwable cause) {
        super(cause);
    }

}
