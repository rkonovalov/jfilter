package com.json.ignore.strategy;

import mock.MockMethods;
import org.junit.Test;
import org.springframework.core.MethodParameter;
import java.lang.reflect.Method;
import static org.junit.Assert.*;

public class IgnoreFactoryTest {

    @Test
    public void testIgnoreSettingMethod() {
        Method method = MockMethods.findMethodByName("mockIgnoreSettingsMethod");
        assertNotNull(method);

        MethodParameter methodParameter = new MethodParameter(method, 0);
        assertTrue(IgnoreFactory.isAccept(methodParameter));
    }

    @Test
    public void testStrategiesMethod() {
        Method method = MockMethods.findMethodByName("mockIgnoreStrategiesMethod");
        assertNotNull(method);

        MethodParameter methodParameter = new MethodParameter(method, 0);
        assertTrue(IgnoreFactory.isAccept(methodParameter));
    }

    @Test
    public void testFactoryFieldIgnore() {
        Method method = MockMethods.findMethodByName("mockIgnoreSettingsMethod");
        assertNotNull(method);

        MethodParameter methodParameter = new MethodParameter(method, 0);

        Ignore ignore = IgnoreFactory.getIgnore(null, methodParameter);
        assertNotNull(ignore);

        assertEquals(ignore.getClass(), FieldIgnore.class);
    }

    @Test
    public void testFactoryStrategyIgnore() {
        Method method = MockMethods.findMethodByName("mockIgnoreStrategiesMethod");
        assertNotNull(method);

        MethodParameter methodParameter = new MethodParameter(method, 0);

        Ignore ignore = IgnoreFactory.getIgnore(null, methodParameter);
        assertNotNull(ignore);

        assertEquals(ignore.getClass(), StrategyIgnore.class);
    }
}
