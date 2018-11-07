package com.json.ignore.advice;

import com.json.ignore.mock.MockClasses;
import com.json.ignore.mock.MockHttpRequest;
import com.json.ignore.mock.MockUser;
import com.json.ignore.mock.config.WSConfigurationDisabled;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static com.json.ignore.mock.webservice.WSClassFieldSingle.MAPPING_SIGN_IN_FIELD_SINGLE;
import static org.junit.Assert.assertNotEquals;

@ContextConfiguration(classes = WSConfigurationDisabled.class)
@RunWith(SpringRunner.class)
@WebAppConfiguration("src/main/resources")
public class WSDisabledITTest {
    private MockMvc mockMvc;
    private WebApplicationContext webApplicationContext;

    @Autowired
    public WSDisabledITTest setWebApplicationContext(WebApplicationContext webApplicationContext) {
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

        assertNotEquals(user.toString(), result);
    }



}
