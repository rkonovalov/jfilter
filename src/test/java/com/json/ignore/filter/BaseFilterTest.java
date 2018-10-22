package com.json.ignore.filter;

import com.json.ignore.filter.field.FieldFilter;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class BaseFilterTest {

    @Test(expected = IllegalArgumentException.class)
    public void testNullMethod() {
        BaseFilter baseFilter = new FieldFilter( null);
        assertNotNull(baseFilter);
    }
}
