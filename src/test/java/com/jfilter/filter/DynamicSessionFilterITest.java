package com.jfilter.filter;

import com.jfilter.components.DynamicFilterProvider;
import com.jfilter.mock.MockHttpRequest;
import com.jfilter.mock.MockMethods;
import com.jfilter.mock.config.WSConfigurationEnabled;
import com.jfilter.request.RequestSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import static org.junit.Assert.assertNotNull;

@ContextConfiguration(classes = WSConfigurationEnabled.class)
@RunWith(SpringRunner.class)
@WebAppConfiguration("src/main/resources")
public class DynamicSessionFilterITest {

    @Autowired
    private DynamicFilterProvider dynamicFilterProvider;

    @Test
    public void testOnGetFilterFieldsNotNull() {
        MethodParameter methodParameter = MockMethods.dynamicSessionFilter();
        ServletServerHttpRequest request = MockHttpRequest.getMockDynamicFilterRequest(null);
        FilterFields found = dynamicFilterProvider.getFields(methodParameter, new RequestSession(request));

        assertNotNull(found);
    }

    @Test
    public void testMockDynamicNullFilter() {
        MethodParameter methodParameter = MockMethods.mockDynamicNullFilter();

        RequestSession requestSession = new RequestSession(MockHttpRequest.getMockDynamicFilterRequest(null));

        FilterFields found = dynamicFilterProvider.getFields(methodParameter, requestSession);

        assertNotNull(found);
    }
}
