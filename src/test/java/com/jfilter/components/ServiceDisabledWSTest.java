package com.jfilter.components;

import com.jfilter.mock.webservice.WSClassFieldSingle;
import com.jfilter.mock.MockClassesHelper;
import com.jfilter.mock.MockHttpRequestHelper;
import com.jfilter.mock.MockUser;
import com.jfilter.mock.config.WSConfigurationDisabled;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertNotEquals;

@ContextConfiguration(classes = WSConfigurationDisabled.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class ServiceDisabledWSTest {
    private MockMvc mockMvc;
    private WebApplicationContext webApplicationContext;

    @Autowired
    public ServiceDisabledWSTest setWebApplicationContext(WebApplicationContext webApplicationContext) {
        this.webApplicationContext = webApplicationContext;
        return this;
    }

    @Before
    public void init() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void testWSClassFieldSingle() {
        MockUser user = MockClassesHelper.getUserMock();
        user.setId(null);

        String result = MockHttpRequestHelper.doRequest(mockMvc, WSClassFieldSingle.MAPPING_SIGN_IN_FIELD_SINGLE, "ROLE", "ADMIN");

        assertNotEquals(user.toString(), result);
    }



}
