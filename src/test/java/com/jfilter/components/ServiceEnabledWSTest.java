package com.jfilter.components;

import com.jfilter.mock.webservice.WSClassFieldSingle;
import com.jfilter.mock.MockClassesHelper;
import com.jfilter.mock.MockHttpRequestHelper;
import com.jfilter.mock.MockUser;
import com.jfilter.mock.config.WSConfigurationEnabled;
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
import static com.jfilter.mock.webservice.WSClassFieldMultiple.MAPPING_SIGN_IN_FIELD_MULTIPLE;
import static com.jfilter.mock.webservice.WSClassStrategies.MAPPING_SIGN_IN_STRATEGIES;
import static com.jfilter.mock.webservice.WSClassStrategyMultiple.MAPPING_SIGN_IN_STRATEGY_MULTIPLE;
import static com.jfilter.mock.webservice.WSClassStrategySingle.MAPPING_SIGN_IN_STRATEGY_SINGLE;
import static com.jfilter.mock.webservice.WSMethod.MAPPING_SIGN_IN_DEFAULT;
import static junit.framework.TestCase.assertEquals;

@ContextConfiguration(classes = WSConfigurationEnabled.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class ServiceEnabledWSTest {
    private MockMvc mockMvc;
    private WebApplicationContext webApplicationContext;

    @Autowired
    public ServiceEnabledWSTest setWebApplicationContext(WebApplicationContext webApplicationContext) {
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

        assertEquals(user.toString(), result);
    }

    @Test
    public void testWSClassFieldMultiple() {
        MockUser user = MockClassesHelper.getUserMock();
        user.setId(null)
                .setPassword(null);

        String result = MockHttpRequestHelper.doRequest(mockMvc, MAPPING_SIGN_IN_FIELD_MULTIPLE, "ROLE", "ADMIN");

        assertEquals(user.toString(), result);
    }

    @Test
    public void testWSClassStrategySingle() {
        MockUser user = MockClassesHelper.getUserMock();
        user.setId(null)
                .setPassword(null);

        String result = MockHttpRequestHelper.doRequest(mockMvc, MAPPING_SIGN_IN_STRATEGY_SINGLE, "ROLE", "ADMIN");

        assertEquals(user.toString(), result);
    }

    @Test
    public void testWSClassStrategyMultiple() {
        MockUser user = MockClassesHelper.getUserMock();
        user.setId(null)
                .setPassword(null)
                .setEmail(null);

        String result = MockHttpRequestHelper.doRequest(mockMvc, MAPPING_SIGN_IN_STRATEGY_MULTIPLE, "ROLE", "ADMIN");

        assertEquals(user.toString(), result);
    }

    @Test
    public void testWSClassStrategies() {
        MockUser user = MockClassesHelper.getUserMock();
        user.setId(null)
                .setPassword(null)
                .setEmail(null);

        String result = MockHttpRequestHelper.doRequest(mockMvc, MAPPING_SIGN_IN_STRATEGIES, "ROLE", "ADMIN");

        assertEquals(user.toString(), result);
    }

    @Test
    public void testWSSignInDefault() {
        MockUser user = MockClassesHelper.getUserMock();

        String result = MockHttpRequestHelper.doRequest(mockMvc, MAPPING_SIGN_IN_DEFAULT, "ROLE", "ADMIN");

        assertEquals(user.toString(), result);
    }
}
