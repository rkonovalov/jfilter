package com.json.ignore.filter;

import com.json.ignore.filter.field.FieldFilter;
import com.json.ignore.filter.strategy.StrategyFilter;
import com.json.ignore.mock.MockMethods;
import org.junit.Test;
import org.springframework.core.MethodParameter;

import static org.junit.Assert.*;

public class FilterFactoryTest {

    @Test
    public void testIgnoreSettingMethod() {
        MethodParameter methodParameter = MockMethods.mockIgnoreSettingsMethod();
        assertTrue(FilterFactory.isAccept(methodParameter));
    }

    @Test
    public void testIgnoreWithoutAnnotations() {
        MethodParameter methodParameter = MockMethods.methodWithoutAnnotations();
        assertFalse(FilterFactory.isAccept(methodParameter));
    }

    @Test
    public void testStrategiesMethod() {
        MethodParameter methodParameter = MockMethods.mockIgnoreStrategiesMethod();
        assertTrue(FilterFactory.isAccept(methodParameter));
    }

    @Test
    public void testFactoryFieldIgnore() {
        MethodParameter methodParameter = MockMethods.mockIgnoreSettingsMethod();
        assertNotNull(methodParameter);

        BaseFilter filter = FilterFactory.getFromFactory(methodParameter);
        assertNotNull(filter);

        assertEquals(filter.getClass(), FieldFilter.class);
    }

    @Test
    public void testFactoryStrategyFilter() {
        MethodParameter methodParameter = MockMethods.mockIgnoreStrategiesMethod();
        assertNotNull(methodParameter);

        BaseFilter filter = FilterFactory.getFromFactory(methodParameter);
        assertNotNull(filter);

        assertEquals(filter.getClass(), StrategyFilter.class);
    }

    @Test
    public void testFactoryNull() {
        MethodParameter methodParameter = MockMethods.methodWithoutAnnotations();
        assertNotNull(methodParameter);
        BaseFilter filter = FilterFactory.getFromFactory(methodParameter);
        assertNull(filter);
    }
}
