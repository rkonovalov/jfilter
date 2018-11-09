package com.json.ignore;

import org.junit.Test;
import static org.junit.Assert.assertNotNull;

public class FilterExceptionTest {

    @Test(expected = FilterException.class)
    public void testException() {
        FilterException exception =new FilterException(new IllegalAccessException());
        assertNotNull(exception);

        throw exception;
    }
}
