package com.jfilter.filter;

import org.junit.Test;
import static org.junit.Assert.assertNotNull;

public class BaseFilterTest {

    @Test(expected = IllegalArgumentException.class)
    public void testNullMethod() {
        BaseFilter baseFilter = new FieldFilter(null);
        assertNotNull(baseFilter);
    }
}
