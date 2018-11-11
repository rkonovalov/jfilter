package com.jfilter.components;

import com.jfilter.filter.FilterFields;
import com.jfilter.mock.MockHttpRequest;
import com.jfilter.mock.MockMethods;
import com.jfilter.mock.MockUser;
import com.jfilter.mock.config.WSConfigurationEnabled;
import com.jfilter.request.RequestSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import java.util.Arrays;
import static org.junit.Assert.assertEquals;

@ContextConfiguration(classes = WSConfigurationEnabled.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class ConverterMapperITest {
    @Autowired
    private DynamicFilterProvider dynamicFilterProvider;

    @Test
    public void testOnGetFilterFieldsTrue() {
        FilterFields filterFields = new FilterFields(MockUser.class, Arrays.asList("id", "password"));

        MethodParameter methodParameter = MockMethods.dynamicSessionFilter();
        ServletServerHttpRequest request = MockHttpRequest.getMockDynamicFilterRequest(filterFields);
        FilterFields found = dynamicFilterProvider.getFields(methodParameter, new RequestSession(request));

        assertEquals(filterFields, found);
    }

    @Test
    public void testOnGetFilterFieldsEmpty() {
        MethodParameter methodParameter = MockMethods.methodWithoutAnnotations();
        ServletServerHttpRequest request = MockHttpRequest.getMockDynamicFilterRequest(null);
        FilterFields found = dynamicFilterProvider.getFields(methodParameter, new RequestSession(request));

        assertEquals(0, found.getFieldsMap().size());
    }
}
