package com.json.ignore.filter;

import org.junit.Test;
import java.util.Arrays;
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

    @Test
    public void testHashCodeEquals() {
        FilterFields filterFields = new FilterFields(String.class, Arrays.asList("id", "password"));
        FilterFields filterFields2 = new FilterFields(String.class, Arrays.asList("id", "password"));

        assertNotSame(filterFields, filterFields2);
        assertEquals(filterFields.hashCode(), filterFields2.hashCode());
    }

    @Test
    public void testHashCodeNotEquals() {
        FilterFields filterFields = new FilterFields(String.class, Arrays.asList("id", "password"));
        FilterFields filterFields2 = new FilterFields(String.class, Arrays.asList("some_id", "some_password"));

        assertNotSame(filterFields, filterFields2);
        assertNotEquals(filterFields.hashCode(), filterFields2.hashCode());
    }
}
