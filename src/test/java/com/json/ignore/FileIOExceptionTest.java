package com.json.ignore;

import org.junit.Test;

public class FileIOExceptionTest {

    @Test(expected = FileIOException.class)
    public void testException() {
        throw new FileIOException(new IllegalAccessException());
    }
}
