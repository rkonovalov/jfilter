package com.jfilter.filter;

import com.jfilter.mock.MockMethods;
import org.junit.Test;
import org.springframework.core.MethodParameter;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class FilterFactoryTest {

    @Test(expected = IllegalAccessException.class)
    public void testInit() throws IllegalAccessException, InstantiationException {
        FilterFactory filterFactory = FilterFactory.class.newInstance();
        assertNotNull(filterFactory);
    }

    @Test
    public void testIgnoreSettingMethod() {
        MethodParameter methodParameter = MockMethods.mockIgnoreSettingsMethod(null);
        assertTrue(FilterFactory.isAccept(methodParameter));
    }

    @Test
    public void testIgnoreWithoutAnnotations() {
        MethodParameter methodParameter = MockMethods.methodWithoutAnnotations(null);
        assertFalse(FilterFactory.isAccept(methodParameter));
    }

    @Test
    public void testStrategiesMethod() {
        MethodParameter methodParameter = MockMethods.mockIgnoreStrategiesMethod(null);
        assertTrue(FilterFactory.isAccept(methodParameter));
    }

    @Test
    public void testFactoryFieldIgnore() {
        MethodParameter methodParameter = MockMethods.mockIgnoreSettingsMethod(null);
        assertNotNull(methodParameter);

        BaseFilter filter = FilterFactory.getFromFactory(methodParameter);
        assertNotNull(filter);

        assertEquals(filter.getClass(), FieldFilter.class);
    }

    @Test
    public void testFactoryStrategyFilter() {
        MethodParameter methodParameter = MockMethods.mockIgnoreStrategiesMethod(null);
        assertNotNull(methodParameter);

        BaseFilter filter = FilterFactory.getFromFactory(methodParameter);
        assertNotNull(filter);

        assertEquals(filter.getClass(), StrategyFilter.class);
    }

    @Test
    public void testFactoryNull() {
        MethodParameter methodParameter = MockMethods.methodWithoutAnnotations(null);
        assertNotNull(methodParameter);
        BaseFilter filter = FilterFactory.getFromFactory(methodParameter);
        assertNull(filter);
    }
}
