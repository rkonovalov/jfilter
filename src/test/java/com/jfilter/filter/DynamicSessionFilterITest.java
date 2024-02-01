package com.jfilter.filter;

import com.jfilter.components.DynamicFilterProvider;
import com.jfilter.mock.MockHttpRequestHelper;
import com.jfilter.mock.MockMethods;
import com.jfilter.mock.MockUser;
import com.jfilter.mock.config.WSConfigurationHelper;
import com.jfilter.request.RequestSession;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

@Component
public class DynamicSessionFilterITest {

    @Autowired
    private DynamicFilterProvider dynamicFilterProvider;

    @Test
    public void testOnGetFilterFieldsNotNull() throws Exception {
        WSConfigurationHelper.instance(WSConfigurationHelper.Instance.FILTER_ENABLED, this);
        MethodParameter methodParameter = MockMethods.dynamicSessionFilter(null);
        HttpServletRequest request = MockHttpRequestHelper.getMockDynamicFilterRequest(null);
        FilterFields found = dynamicFilterProvider.getFields(methodParameter, new RequestSession(request));

        assertNotNull(found);
    }

    @Test
    public void testMockDynamicNullFilter() throws Exception {
        WSConfigurationHelper.instance(WSConfigurationHelper.Instance.FILTER_ENABLED, this);
        MethodParameter methodParameter = MockMethods.mockDynamicNullFilter(null);

        RequestSession requestSession = new RequestSession(MockHttpRequestHelper.getMockDynamicFilterRequest(new FilterFields(MockUser.class, Arrays.asList("id", "password"))));
        
        FilterFields found = dynamicFilterProvider.getFields(methodParameter, requestSession);

        assertEquals(FilterFields.EMPTY_FIELDS.get(), found);
    }

    @Test
    public void testDynamicSessionFilter() throws Exception {
        WSConfigurationHelper.instance(WSConfigurationHelper.Instance.FILTER_ENABLED, this);
        MethodParameter methodParameter = MockMethods.dynamicSessionFilter(null);

        RequestSession requestSession = new RequestSession(MockHttpRequestHelper.getMockDynamicFilterRequest(new FilterFields(MockUser.class, Arrays.asList("id", "password"))));

        FilterFields found = dynamicFilterProvider.getFields(methodParameter, requestSession);

        assertNotNull(found);
    }
}
