package com.jfilter.components;

import com.jfilter.mock.MockClasses;
import com.jfilter.mock.MockHttpRequest;
import com.jfilter.mock.MockUser;
import com.jfilter.mock.config.WSConfiguration;
import com.jfilter.mock.config.WSConfigurationEnabled;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.jfilter.mock.webservice.WSDynamicFilter.MAPPING_DYNAMIC_REQUEST_ATTRIBUTE_FIELDS;
import static com.jfilter.mock.webservice.WSDynamicFilter.MAPPING_DYNAMIC_SESSION_ATTRIBUTE_FIELDS;
import static junit.framework.TestCase.assertEquals;

@ContextConfiguration(classes = WSConfigurationEnabled.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class DynamicSessionFilterITest {
    private MockMvc mockMvc;

    @Autowired
    public void setWebApplicationContext(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testDynamicSessionAttributeFields() throws Exception {
        WSConfiguration.instance(WSConfiguration.Instance.FILTER_ENABLED, this);
        MockUser user = MockClasses.getUserMock();
        user.setId(null)
                .setEmail(null)
                .setPassword(null);
        String result = MockHttpRequest.doRequest(mockMvc, MAPPING_DYNAMIC_SESSION_ATTRIBUTE_FIELDS);
        assertEquals(user.toString(), result);
    }

    @Test
    public void testDynamicRequestAttributeFields() throws Exception {
        WSConfiguration.instance(WSConfiguration.Instance.FILTER_ENABLED, this);
        MockUser user = MockClasses.getUserMock();
        user.setId(null)
                .setEmail(null)
                .setPassword(null);
        String result = MockHttpRequest.doRequest(mockMvc, MAPPING_DYNAMIC_REQUEST_ATTRIBUTE_FIELDS);
        assertEquals(user.toString(), result);
    }
}
