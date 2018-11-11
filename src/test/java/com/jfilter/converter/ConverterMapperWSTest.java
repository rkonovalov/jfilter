package com.jfilter.converter;

import com.jfilter.components.DynamicFilterProvider;
import com.jfilter.filter.FilterFields;
import com.jfilter.mock.MockClasses;
import com.jfilter.mock.MockHttpRequest;
import com.jfilter.mock.MockUser;
import com.jfilter.mock.config.WSConfiguration;
import com.jfilter.mock.config.WSConfigurationEnabled;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.util.Arrays;
import static com.jfilter.components.DynamicSessionFilter.ATTRIBUTE_FILTER_FIELDS;
import static com.jfilter.mock.webservice.WSMethod.MAPPING_SIGN_IN_DYNAMIC;
import static org.junit.Assert.assertEquals;


@Component
public class ConverterMapperWSTest {
    @Autowired
    private DynamicFilterProvider dynamicFilterProvider;

    private MockMvc mockMvc;

    @Autowired
    public void setWebApplicationContext(WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testSetFilterFields() throws Exception {
        WSConfiguration.instance(WSConfiguration.Instance.FILTER_ENABLED, this);

        MockUser user = MockClasses.getUserMock();
        String result = MockHttpRequest.doRequest(mockMvc, MAPPING_SIGN_IN_DYNAMIC, null, null);
        assertEquals(user.toString(), result);
    }

    @Test
    public void testNotSetFilterFields() throws Exception {
        WSConfiguration.instance(WSConfiguration.Instance.FILTER_ENABLED, this);

        MockUser user = MockClasses.getUserMock();
        user.setId(null);
        user.setPassword(null);

        FilterFields filterFields = new FilterFields(MockUser.class, Arrays.asList("id", "password"));
        String result = MockHttpRequest.doRequest(mockMvc, MAPPING_SIGN_IN_DYNAMIC, ATTRIBUTE_FILTER_FIELDS, filterFields);
        assertEquals(user.toString(), result);
    }
}
