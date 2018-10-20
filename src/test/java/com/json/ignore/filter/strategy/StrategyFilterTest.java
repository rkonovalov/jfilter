package com.json.ignore.filter.strategy;

import mock.MockHttpRequest;
import mock.MockMethods;
import mock.MockUser;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.server.ServletServerHttpRequest;
import javax.servlet.http.HttpSession;
import static org.junit.Assert.*;

public class StrategyFilterTest {
    private HttpSession session;
    private ServletServerHttpRequest serverHttpRequest;

    @Before
    public void initSession() {
        serverHttpRequest = MockHttpRequest.getMockAdminRequest();
        assertNotNull(serverHttpRequest);

        session = serverHttpRequest.getServletRequest().getSession();
        assertNotNull(session);
    }

    @Test
    public void ignoreFields() {
        MockUser user = new MockUser();
        user.setId(100);
        MethodParameter methodParameter = MockMethods.mockIgnoreStrategiesMethod();
        assertNotNull(methodParameter);

        StrategyFilter strategyFilter = new StrategyFilter(this.session, methodParameter);
        strategyFilter.filter(user);

        assertNull(user.getId());
    }

    @Test
    public void ignoreFieldsWithoutAnnotations() {
        MockUser user = new MockUser();
        user.setId(100);
        MethodParameter methodParameter = MockMethods.methodWithoutAnnotations();
        assertNotNull(methodParameter);

        StrategyFilter strategyFilter = new StrategyFilter(this.session, methodParameter);
        strategyFilter.filter(user);

        assertNotNull(user.getId());
    }

    @Test
    public void ignoreRequestFieldsWithoutAnnotations() {
        MockUser user = new MockUser();
        user.setId(100);
        MethodParameter methodParameter = MockMethods.methodWithoutAnnotations();
        assertNotNull(methodParameter);

        StrategyFilter strategyFilter = new StrategyFilter(this.serverHttpRequest, methodParameter);
        strategyFilter.filter(user);

        assertNotNull(user.getId());
    }
}
