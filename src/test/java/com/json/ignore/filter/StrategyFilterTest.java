package com.json.ignore.filter;

import com.json.ignore.filter.strategy.StrategyFilter;
import com.json.ignore.mock.MockClasses;
import com.json.ignore.mock.MockHttpRequest;
import com.json.ignore.mock.MockMethods;
import com.json.ignore.request.RequestSession;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.MethodParameter;

public class StrategyFilterTest {
    private MethodParameter methodParameter;
    private StrategyFilter strategyFilter;

    @Before
    public void init() {
        methodParameter = MockMethods.mockIgnoreStrategyMethod();
        strategyFilter = new StrategyFilter(methodParameter);
    }

    @Test
    public void testUserSession() {
        RequestSession requestSession = new RequestSession(MockHttpRequest.getMockUserRequest());
        FilterFields filterFields = strategyFilter.getFields(MockClasses.getUserMock(), requestSession);
        Assert.assertEquals(1, filterFields.getFieldsMap().size());
    }

    @Test
    public void testClearSession() {
        RequestSession requestSession = new RequestSession(MockHttpRequest.getMockClearRequest());
        FilterFields filterFields = strategyFilter.getFields(MockClasses.getUserMock(), requestSession);
        Assert.assertEquals(0, filterFields.getFieldsMap().size());
    }
}
