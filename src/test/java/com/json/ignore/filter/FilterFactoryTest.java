package com.json.ignore.filter;

import com.json.ignore.filter.field.FieldFilter;
import com.json.ignore.filter.strategy.StrategyFilter;
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

        BaseFilter filter = FilterFactory.getFromFactory(null, methodParameter);
        assertNotNull(filter);

        assertEquals(filter.getClass(), FieldFilter.class);
    }

    @Test
    public void testFactoryStrategyIgnore() {
        Method method = MockMethods.findMethodByName("mockIgnoreStrategiesMethod");
        assertNotNull(method);

        MethodParameter methodParameter = new MethodParameter(method, 0);

        BaseFilter filter = FilterFactory.getFromFactory(null, methodParameter);
        assertNotNull(filter);

        assertEquals(filter.getClass(), StrategyFilter.class);
    }

    @Test
    public void testFactoryNull() {
        Method method = MockMethods.findMethodByName("methodWithoutAnnotations");
        assertNotNull(method);
        MethodParameter methodParameter = new MethodParameter(method, 0);
        BaseFilter filter = FilterFactory.getFromFactory(null, methodParameter);
        assertNull(filter);


    }
}
