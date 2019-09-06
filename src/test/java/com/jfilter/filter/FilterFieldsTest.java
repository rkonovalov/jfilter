package com.jfilter.filter;

import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class FilterFieldsTest {
    private FilterFields filterFields;
    private FilterFields filterFieldsNull;

    @Before
    public void init() {
        filterFields = new FilterFields(String.class, Arrays.asList("id", "password"));
        filterFieldsNull = new FilterFields(void.class, Arrays.asList("id", "password"));
    }

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
        FilterFields filterFields2 = new FilterFields(String.class, Arrays.asList("id", "password"));

        assertNotSame(filterFields, filterFields2);
        assertEquals(filterFields.hashCode(), filterFields2.hashCode());
    }

    @Test
    public void testHashCodeNotEquals() {
        FilterFields filterFields2 = new FilterFields(String.class, Arrays.asList("some_id", "some_password"));

        assertNotSame(filterFields, filterFields2);
        assertNotEquals(filterFields.hashCode(), filterFields2.hashCode());
    }

    @Test
    public void testEquals() {
        FilterFields filterFields2 = new FilterFields(String.class, Arrays.asList("id", "password"));
        assertEquals(filterFields, filterFields2);
    }

    @Test
    public void testNotEquals() {
        FilterFields filterFields2 = new FilterFields(String.class, Arrays.asList("some_id", "some_password"));
        assertNotEquals(filterFields, filterFields2);
    }

    @Test
    public void testEqualsBehaviour() {
        filterFields.setFilterBehaviour(FilterBehaviour.KEEP_FIELDS);

        FilterFields filterFields2 = new FilterFields(String.class, Arrays.asList("id", "password"));
        filterFields2.setFilterBehaviour(FilterBehaviour.KEEP_FIELDS);
        assertEquals(filterFields, filterFields2);
    }

    @Test
    public void testNotEqualsBehaviour() {
        filterFields.setFilterBehaviour(FilterBehaviour.KEEP_FIELDS);

        FilterFields filterFields2 = new FilterFields(String.class, Arrays.asList("id", "password"));
        filterFields2.setFilterBehaviour(FilterBehaviour.HIDE_FIELDS);
        assertNotEquals(filterFields, filterFields2);
    }

    @Test
    public void testgetFieldsByClassAndList() {
        FilterFields filterFields2 = FilterFields.getFieldsBy(String.class, Arrays.asList("id", "password"));

        assertEquals(filterFields, filterFields2);
    }

    @Test
    public void testgetFieldsByList() {
        FilterFields filterFields2 = FilterFields.getFieldsBy(Arrays.asList("id", "password"));

        assertEquals(filterFieldsNull, filterFields2);
    }

    @Test
    public void testgetFieldsByClassAndArray() {
        FilterFields filterFields2 = FilterFields.getFieldsBy(String.class, new String[]{"id", "password"});

        assertEquals(filterFields, filterFields2);
    }

    @Test
    public void testgetFieldsByArray() {
        FilterFields filterFields2 = FilterFields.getFieldsBy(new String[]{"id", "password"});

        assertEquals(filterFieldsNull, filterFields2);
    }
}
