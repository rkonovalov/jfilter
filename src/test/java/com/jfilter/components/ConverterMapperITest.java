package com.jfilter.components;

import com.jfilter.filter.FilterFields;
import com.jfilter.mock.MockClasses;
import com.jfilter.mock.MockHttpRequest;
import com.jfilter.mock.MockMethods;
import com.jfilter.mock.MockUser;
import com.jfilter.mock.config.WSConfigurationEnabled;
import com.jfilter.request.RequestSession;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static com.jfilter.components.DynamicSessionFilter.ATTRIBUTE_FILTER_FIELDS;
import static com.jfilter.mock.webservice.WSMethod.MAPPING_SIGN_IN_DYNAMIC;
import static org.junit.Assert.assertEquals;

@ContextConfiguration(classes = WSConfigurationEnabled.class)
@RunWith(SpringRunner.class)
@WebAppConfiguration("src/main/resources")
public class ConverterMapperITest {
    @Autowired
    private DynamicFilterProvider dynamicFilterProvider;

    private MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

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

    @Test
    public void testSetFilterFields() {
        MockUser user = MockClasses.getUserMock();

        String result = MockHttpRequest.doRequest(mockMvc, MAPPING_SIGN_IN_DYNAMIC, null, null);

        assertEquals(user.toString(), result);
    }

  //  @Test
    public void testNotSetFilterFields() {
        MockUser user = MockClasses.getUserMock();
        user.setId(null);
        user.setPassword(null);

        FilterFields filterFields = new FilterFields(MockUser.class, Arrays.asList("id", "password"));

        String result = MockHttpRequest.doRequest(mockMvc, MAPPING_SIGN_IN_DYNAMIC, ATTRIBUTE_FILTER_FIELDS, filterFields);

        assertEquals(user.toString(), result);
    }
}
