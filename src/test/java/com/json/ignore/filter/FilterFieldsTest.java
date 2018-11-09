package com.json.ignore.filter;

import org.junit.Test;

import static org.junit.Assert.*;

public class FilterFieldsTest {

    @Test
    public void testEqualsTrue() {
        FilterFields filterFields = new FilterFields();
        boolean result = filterFields.equals(new FilterFields());

        assertTrue(result);
    }

    @SuppressWarnings("EqualsBetweenInconvertibleTypes")
    @Test
    public void testEqualsFalse() {
        FilterFields filterFields = new FilterFields();
        boolean result = filterFields.equals(1);

        assertFalse(result);
    }
}
