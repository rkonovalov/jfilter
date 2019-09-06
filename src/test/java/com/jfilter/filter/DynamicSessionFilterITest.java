package com.jfilter.filter;

import com.jfilter.components.DynamicFilterProvider;
import com.jfilter.mock.MockHttpRequest;
import com.jfilter.mock.MockMethods;
import com.jfilter.mock.MockUser;
import com.jfilter.mock.config.WSConfiguration;
import com.jfilter.request.RequestSession;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

@Component
public class DynamicSessionFilterITest {

    @Autowired
    private DynamicFilterProvider dynamicFilterProvider;

    @Test
    public void testOnGetFilterFieldsNotNull() throws Exception {
        WSConfiguration.instance(WSConfiguration.Instance.FILTER_ENABLED, this);
        MethodParameter methodParameter = MockMethods.dynamicSessionFilter();
        HttpServletRequest request = MockHttpRequest.getMockDynamicFilterRequest(null);
        FilterFields found = dynamicFilterProvider.getFields(methodParameter, new RequestSession(request));

        assertNotNull(found);
    }

    @Test
    public void testMockDynamicNullFilter() throws Exception {
        WSConfiguration.instance(WSConfiguration.Instance.FILTER_ENABLED, this);
        MethodParameter methodParameter = MockMethods.mockDynamicNullFilter();

        RequestSession requestSession = new RequestSession(MockHttpRequest.getMockDynamicFilterRequest(new FilterFields(MockUser.class, Arrays.asList("id", "password"))));
        
        FilterFields found = dynamicFilterProvider.getFields(methodParameter, requestSession);

        assertEquals(FilterFields.EMPTY_FIELDS, found);
    }

    @Test
    public void testDynamicSessionFilter() throws Exception {
        WSConfiguration.instance(WSConfiguration.Instance.FILTER_ENABLED, this);
        MethodParameter methodParameter = MockMethods.dynamicSessionFilter();

        RequestSession requestSession = new RequestSession(MockHttpRequest.getMockDynamicFilterRequest(new FilterFields(MockUser.class, Arrays.asList("id", "password"))));

        FilterFields found = dynamicFilterProvider.getFields(methodParameter, requestSession);

        assertNotNull(found);
    }
}
