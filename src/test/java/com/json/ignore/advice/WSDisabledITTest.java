package com.json.ignore.advice;

import com.json.ignore.mock.MockClasses;
import com.json.ignore.mock.MockHttpRequest;
import com.json.ignore.mock.MockUser;
import com.json.ignore.mock.config.WSConfigurationDisabled;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.junit.Assert.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ContextConfiguration(classes = WSConfigurationDisabled.class)
@RunWith(SpringRunner.class)
@WebAppConfiguration("src/main/resources")
public class WSDisabledITTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void init() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testWSClassFieldSingle() {
        MockUser user = MockClasses.getUserMock();
        user.setId(null);

        //Build mock request
        MockHttpServletRequestBuilder requestBuilder = post("/field-single/customers/signIn");
        requestBuilder.sessionAttr("ROLE", "ADMIN")
                .param("email", "email")
                .param("password", "password")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED);

        String result = MockHttpRequest.getContent(mockMvc, requestBuilder);
        assertNotEquals(user.toString(), result);
    }



}
