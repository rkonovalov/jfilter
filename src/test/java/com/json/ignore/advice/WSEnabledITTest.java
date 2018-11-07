package com.json.ignore.advice;

import com.json.ignore.mock.MockClasses;
import com.json.ignore.mock.MockHttpRequest;
import com.json.ignore.mock.MockUser;
import com.json.ignore.mock.config.WSConfigurationEnabled;
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
import static com.json.ignore.mock.webservice.WSClassFieldMultiple.MAPPING_SIGN_IN_FIELD_MULTIPLE;
import static com.json.ignore.mock.webservice.WSClassFieldSingle.MAPPING_SIGN_IN_FIELD_SINGLE;
import static com.json.ignore.mock.webservice.WSClassStrategies.MAPPING_SIGN_IN_STRATEGIES;
import static com.json.ignore.mock.webservice.WSClassStrategyMultiple.MAPPING_SIGN_IN_STRATEGY_MULTIPLE;
import static com.json.ignore.mock.webservice.WSClassStrategySingle.MAPPING_SIGN_IN_STRATEGY_SINGLE;
import static com.json.ignore.mock.webservice.WSMethod.MAPPING_SIGN_IN_DEFAULT;
import static junit.framework.TestCase.assertEquals;

@ContextConfiguration(classes = WSConfigurationEnabled.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("src/main/resources")
public class WSEnabledITTest {
    private MockMvc mockMvc;
    private WebApplicationContext webApplicationContext;

    @Autowired
    public WSEnabledITTest setWebApplicationContext(WebApplicationContext webApplicationContext) {
        this.webApplicationContext = webApplicationContext;
        return this;
    }

    @Before
    public void init() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void testWSClassFieldSingle() {
        MockUser user = MockClasses.getUserMock();
        user.setId(null);

        String result = MockHttpRequest.doRequest(mockMvc, MAPPING_SIGN_IN_FIELD_SINGLE, "ROLE", "ADMIN");

        assertEquals(user.toString(), result);
    }

    @Test
    public void testWSClassFieldMultiple() {
        MockUser user = MockClasses.getUserMock();
        user.setId(null)
                .setPassword(null);

        String result = MockHttpRequest.doRequest(mockMvc, MAPPING_SIGN_IN_FIELD_MULTIPLE, "ROLE", "ADMIN");

        assertEquals(user.toString(), result);
    }

    @Test
    public void testWSClassStrategySingle() {
        MockUser user = MockClasses.getUserMock();
        user.setId(null)
                .setPassword(null);

        String result = MockHttpRequest.doRequest(mockMvc, MAPPING_SIGN_IN_STRATEGY_SINGLE, "ROLE", "ADMIN");

        assertEquals(user.toString(), result);
    }

    @Test
    public void testWSClassStrategyMultiple() {
        MockUser user = MockClasses.getUserMock();
        user.setId(null)
                .setPassword(null)
                .setEmail(null);

        String result = MockHttpRequest.doRequest(mockMvc, MAPPING_SIGN_IN_STRATEGY_MULTIPLE, "ROLE", "ADMIN");

        assertEquals(user.toString(), result);
    }

    @Test
    public void testWSClassStrategies() {
        MockUser user = MockClasses.getUserMock();
        user.setId(null)
                .setPassword(null)
                .setEmail(null);

        String result = MockHttpRequest.doRequest(mockMvc, MAPPING_SIGN_IN_STRATEGIES, "ROLE", "ADMIN");

        assertEquals(user.toString(), result);
    }

    @Test
    public void testWSSignInDefault() {
        MockUser user = MockClasses.getUserMock();

        String result = MockHttpRequest.doRequest(mockMvc, MAPPING_SIGN_IN_DEFAULT, "ROLE", "ADMIN");

        assertEquals(user.toString(), result);
    }

}
