package com.json.ignore;

import org.junit.Test;
import static org.junit.Assert.assertNotNull;

public class FieldAccessExceptionTest {

    @Test(expected = FieldAccessException.class)
    public void testException() {
        FieldAccessException exception =new FieldAccessException(new IllegalAccessException());
        assertNotNull(exception);

        throw exception;
    }
}
