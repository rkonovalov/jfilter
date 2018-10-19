package com.json.ignore;

import org.junit.Test;

public class FieldAccessExceptionTest {

    @Test(expected = FieldAccessException.class)
    public void testException() {
        throw new FieldAccessException(new IllegalAccessException());
    }
}
