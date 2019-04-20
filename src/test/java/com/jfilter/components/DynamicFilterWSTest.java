package com.jfilter.components;

import com.jfilter.filter.FilterFields;
import com.jfilter.mock.MockClasses;
import com.jfilter.mock.MockHttpRequest;
import com.jfilter.mock.MockUser;
import com.jfilter.mock.config.WSConfiguration;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static com.jfilter.components.DynamicSessionFilter.ATTRIBUTE_FILTER_FIELDS;
import static com.jfilter.mock.webservice.WSDynamicFilter.MAPPING_NOT_NULL_DYNAMIC_FILTER;
import static com.jfilter.mock.webservice.WSDynamicFilter.MAPPING_NULL_DYNAMIC_FILTER;
import static junit.framework.TestCase.assertEquals;

@Component
public class DynamicFilterWSTest {
    private MockMvc mockMvc;
    private FilterConfiguration filterConfiguration;

    @Autowired
    public DynamicFilterWSTest setFilterConfiguration(FilterConfiguration filterConfiguration) {
        this.filterConfiguration = filterConfiguration;
        return this;
    }

    @Autowired
    public void setWebApplicationContext(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testWSDynamicNotNullFilter() throws Exception {
        WSConfiguration.instance(WSConfiguration.Instance.FILTER_ENABLED, this);
        filterConfiguration.setEnabled(true);
        MockUser user = MockClasses.getUserMock();
        user.setId(null);
        user.setPassword(null);
        String result = MockHttpRequest.doRequest(mockMvc, MAPPING_NOT_NULL_DYNAMIC_FILTER, ATTRIBUTE_FILTER_FIELDS,
                new FilterFields(MockUser.class, Arrays.asList("id", "password")));
        assertEquals(user.toString(), result);
    }

    @Test
    public void testWSDynamicNullFilter() throws Exception {
        WSConfiguration.instance(WSConfiguration.Instance.FILTER_ENABLED, this);
        MockUser user = MockClasses.getUserMock();
        String result = MockHttpRequest.doRequest(mockMvc, MAPPING_NULL_DYNAMIC_FILTER, ATTRIBUTE_FILTER_FIELDS,
                new FilterFields(MockUser.class, Arrays.asList("id", "password")));
        assertEquals(user.toString(), result);
    }

}
