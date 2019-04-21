package com.jfilter.filter;

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

    @Test
    public void testEquals() {
        FilterFields filterFields = new FilterFields(String.class, Arrays.asList("id", "password"));
        FilterFields filterFields2 = new FilterFields(String.class, Arrays.asList("id", "password"));
        assertEquals(filterFields, filterFields2);
    }

    @Test
    public void testNotEquals() {
        FilterFields filterFields = new FilterFields(String.class, Arrays.asList("id", "password"));
        FilterFields filterFields2 = new FilterFields(String.class, Arrays.asList("some_id", "some_password"));
        assertNotEquals(filterFields, filterFields2);
    }

    @Test
    public void testEqualsBehaviour() {
        FilterFields filterFields = new FilterFields(String.class, Arrays.asList("id", "password"));
        filterFields.setFilterBehaviour(FilterBehaviour.KEEP_FIELDS);

        FilterFields filterFields2 = new FilterFields(String.class, Arrays.asList("id", "password"));
        filterFields2.setFilterBehaviour(FilterBehaviour.KEEP_FIELDS);
        assertEquals(filterFields, filterFields2);
    }

    @Test
    public void testNotEqualsBehaviour() {
        FilterFields filterFields = new FilterFields(String.class, Arrays.asList("id", "password"));
        filterFields.setFilterBehaviour(FilterBehaviour.KEEP_FIELDS);

        FilterFields filterFields2 = new FilterFields(String.class, Arrays.asList("id", "password"));
        filterFields2.setFilterBehaviour(FilterBehaviour.HIDE_FIELDS);
        assertNotEquals(filterFields, filterFields2);
    }
}
