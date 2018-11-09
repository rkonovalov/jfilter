package com.json.ignore.filter.dynamic;

import com.json.ignore.advice.DynamicFilterProvider;
import com.json.ignore.filter.FilterFields;
import com.json.ignore.mock.MockClasses;
import com.json.ignore.mock.MockHttpRequest;
import com.json.ignore.mock.MockMethods;
import com.json.ignore.mock.MockUser;
import com.json.ignore.mock.config.WSConfigurationEnabled;
import com.json.ignore.request.RequestSession;
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

import static com.json.ignore.filter.dynamic.DynamicSessionFilter.ATTRIBUTE_FILTER_FIELDS;
import static com.json.ignore.mock.webservice.WSMethod.MAPPING_SIGN_IN_DYNAMIC;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@ContextConfiguration(classes = WSConfigurationEnabled.class)
@RunWith(SpringRunner.class)
@WebAppConfiguration("src/main/resources")
public class DynamicSessionFilterITest {

    @Autowired
    private DynamicFilterProvider dynamicFilterProvider;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void init() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void testOnGetFilterFieldsNotNull() {
        MethodParameter methodParameter = MockMethods.dynamicSessionFilter();
        ServletServerHttpRequest request = MockHttpRequest.getMockDynamicFilterRequest(null);
        FilterFields found = dynamicFilterProvider.getFields(methodParameter, new RequestSession(request));

        assertNotNull(found);
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

    @Test
    public void testOnGetFilterFieldsTrue() {
        FilterFields filterFields = new FilterFields(MockUser.class, Arrays.asList("id", "password"));

        MethodParameter methodParameter = MockMethods.dynamicSessionFilter();
        ServletServerHttpRequest request = MockHttpRequest.getMockDynamicFilterRequest(filterFields);
        FilterFields found = dynamicFilterProvider.getFields(methodParameter, new RequestSession(request));

        assertEquals(filterFields, found);
    }

    @Test
    public void testNotSetFilterFields() {
        MockUser user = MockClasses.getUserMock();
        user.setId(null);
        user.setPassword(null);

        FilterFields filterFields = new FilterFields(MockUser.class, Arrays.asList("id", "password"));

        String result = MockHttpRequest.doRequest(mockMvc, MAPPING_SIGN_IN_DYNAMIC, ATTRIBUTE_FILTER_FIELDS, filterFields);

        assertEquals(user.toString(), result);
    }



}
