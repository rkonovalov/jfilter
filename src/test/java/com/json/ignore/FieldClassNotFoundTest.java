package com.json.ignore;
import org.junit.Test;

public class FieldClassNotFoundTest {

    @Test(expected = FieldClassNotFoundException.class)
    public void testException() {
        throw new FieldClassNotFoundException(new IllegalAccessException());
    }
}
