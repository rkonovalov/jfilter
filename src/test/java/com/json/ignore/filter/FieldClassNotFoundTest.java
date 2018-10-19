package com.json.ignore.filter;
import com.json.ignore.FieldClassNotFoundException;
import org.junit.Test;

public class FieldClassNotFoundTest {

    @Test(expected = FieldClassNotFoundException.class)
    public void testException() {
        throw new FieldClassNotFoundException(new IllegalAccessException());
    }
}
