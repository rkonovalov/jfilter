package com.json.ignore.filter.strategy;

import com.json.ignore.mock.MockHttpRequest;
import com.json.ignore.mock.MockMethods;
import com.json.ignore.mock.MockUser;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.server.ServletServerHttpRequest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class StrategyFilterTest {
    private ServletServerHttpRequest request;

    @Before
    public void initSession() {
        request = MockHttpRequest.getMockAdminRequest();
        assertNotNull(request);
    }

    @Test
    public void ignoreFields() {
        MockUser user = new MockUser();
        user.setId(100);
        MethodParameter methodParameter = MockMethods.mockIgnoreStrategiesMethod();
        assertNotNull(methodParameter);

        StrategyFilter strategyFilter = new StrategyFilter(methodParameter);
        strategyFilter.filter(user, request);
        assertNull(user.getId());
    }

    @Test
    public void ignoreFieldsWithoutAnnotations() {
        MockUser user = new MockUser();
        user.setId(100);
        MethodParameter methodParameter = MockMethods.methodWithoutAnnotations();
        assertNotNull(methodParameter);

        StrategyFilter strategyFilter = new StrategyFilter(methodParameter);
        strategyFilter.filter(user, request);

        assertNotNull(user.getId());
    }


    @Test
    public void testConfigNull() {
        MockUser user = new MockUser();
        MethodParameter methodParameter = MockMethods.methodWithoutAnnotations();
        assertNotNull(methodParameter);

        StrategyFilter strategyFilter = new StrategyFilter(methodParameter);
        strategyFilter.filter(user, request);
        assertEquals(new MockUser(), user);
    }
}
