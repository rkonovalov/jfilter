package com.json.ignore.strategy;

import com.json.ignore.filter.FieldFilter;
import com.json.ignore.filter.Filter;
import com.json.ignore.filter.FilterFactory;
import com.json.ignore.filter.StrategyFilter;
import mock.MockMethods;
import org.junit.Test;
import org.springframework.core.MethodParameter;
import java.lang.reflect.Method;
import static org.junit.Assert.*;

public class FilterFactoryTest {

    @Test
    public void testIgnoreSettingMethod() {
        Method method = MockMethods.findMethodByName("mockIgnoreSettingsMethod");
        assertNotNull(method);

        MethodParameter methodParameter = new MethodParameter(method, 0);
        assertTrue(FilterFactory.isAccept(methodParameter));
    }

    @Test
    public void testStrategiesMethod() {
        Method method = MockMethods.findMethodByName("mockIgnoreStrategiesMethod");
        assertNotNull(method);

        MethodParameter methodParameter = new MethodParameter(method, 0);
        assertTrue(FilterFactory.isAccept(methodParameter));
    }

    @Test
    public void testFactoryFieldIgnore() {
        Method method = MockMethods.findMethodByName("mockIgnoreSettingsMethod");
        assertNotNull(method);

        MethodParameter methodParameter = new MethodParameter(method, 0);

        Filter filter = FilterFactory.getIgnore(null, methodParameter);
        assertNotNull(filter);

        assertEquals(filter.getClass(), FieldFilter.class);
    }

    @Test
    public void testFactoryStrategyIgnore() {
        Method method = MockMethods.findMethodByName("mockIgnoreStrategiesMethod");
        assertNotNull(method);

        MethodParameter methodParameter = new MethodParameter(method, 0);

        Filter filter = FilterFactory.getIgnore(null, methodParameter);
        assertNotNull(filter);

        assertEquals(filter.getClass(), StrategyFilter.class);
    }

    @Test
    public void testFactoryNull() {
        Method method = MockMethods.findMethodByName("methodWithoutAnnotations");
        assertNotNull(method);
        MethodParameter methodParameter = new MethodParameter(method, 0);
        Filter filter = FilterFactory.getIgnore(null, methodParameter);
        assertNull(filter);
    }
}
