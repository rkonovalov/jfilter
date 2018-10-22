package com.json.ignore.advice;

import com.json.ignore.mock.MockClasses;
import com.json.ignore.mock.MockUser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import javax.servlet.ServletContext;
import static junit.framework.TestCase.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ContextConfiguration({"classpath:IntegrationTest-configuration.xml"})
@RunWith(SpringRunner.class)
@WebAppConfiguration("src/main/resources")
public class FilterAdviceITTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void init() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    private String getContent(MockHttpServletRequestBuilder requestBuilder) {
        final StringBuilder result = new StringBuilder();
        try {
            mockMvc.perform(requestBuilder)
                    .andDo(print())
                    .andExpect(mvcResult -> result.append(mvcResult.getResponse().getContentAsString()));
        } catch (Exception e) {
            return result.toString();
        }
        return result.toString();
    }

    @Test
    public void testWebContext() {
        ServletContext servletContext = wac.getServletContext();
        Assert.assertNotNull(servletContext);
    }

    @Test
    public void testWebContextIsMockServletContext() {
        ServletContext servletContext = wac.getServletContext();
        Assert.assertNotNull(servletContext);
        Assert.assertTrue(servletContext instanceof MockServletContext);
    }

    @Test
    public void testSignInSingleAnnotation() {
        MockUser user = MockClasses.getUserMock();
        user.setId(null);
        user.setPassword(null);

        //Build mock request
        MockHttpServletRequestBuilder requestBuilder = post("/customers/signInSingleAnnotation");
        requestBuilder.param("email", "email")
                .param("password", "password")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED);

        String result = getContent(requestBuilder);
        assertEquals(user.toString(), result);
    }

    @Test
    public void testSignInFileAnnotationAdmin() {
        MockUser user = MockClasses.getUserMock();
        user.setId(null);

        //Build mock request
        MockHttpServletRequestBuilder requestBuilder = post("/customers/signInFileAnnotation");
        requestBuilder.sessionAttr("ROLE", "ADMIN")
                .param("email", "email")
                .param("password", "password")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED);

        String result = getContent(requestBuilder);
        assertEquals(user.toString(), result);
    }

    @Test
    public void testSignInUnExistedFile() {
        MockUser user = MockClasses.getUserMock();

        //Build mock request
        MockHttpServletRequestBuilder requestBuilder = post("/customers/signInUnExistedFile");
        requestBuilder.sessionAttr("ROLE", "ADMIN")
                .param("email", "email")
                .param("password", "password")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED);

        String result = getContent(requestBuilder);
        assertEquals(user.toString(), result);
    }

    @Test
    public void testSignInFileAnnotationUser() {
        MockUser user = MockClasses.getUserMock();
        user.setId(null);
        user.setPassword(null);

        //Build mock request
        MockHttpServletRequestBuilder requestBuilder = post("/customers/signInFileAnnotation");
        requestBuilder.sessionAttr("ROLE", "USER")
                .param("email", "email")
                .param("password", "password")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED);

        String result = getContent(requestBuilder);
        assertEquals(user.toString(), result);


    }

    @Test
    public void testSignInStrategyAnnotation() {
        MockUser user = MockClasses.getUserMock();
        user.setId(null);
        user.setPassword(null);

        //Build mock request
        MockHttpServletRequestBuilder requestBuilder = post("/customers/signInStrategyAnnotation");
        requestBuilder.sessionAttr("ROLE", "USER")
                .param("email", "email")
                .param("password", "password")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED);

        String result = getContent(requestBuilder);
        assertEquals(user.toString(), result);
    }


    @Test
    public void testSignInFileClassAnnotationAdmin() {
        MockUser user = MockClasses.getUserMock();
        user.setId(null);

        //Build mock request
        MockHttpServletRequestBuilder requestBuilder = post("/file/customers/signIn");
        requestBuilder.sessionAttr("ROLE", "ADMIN")
                .param("email", "email")
                .param("password", "password")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED);

        String result = getContent(requestBuilder);
        assertEquals(user.toString(), result);
    }

    @Test
    public void testSignInFileClassSingleAnnotationAdmin() {
        MockUser user = MockClasses.getUserMock();
        user.setId(null);

        //Build mock request
        MockHttpServletRequestBuilder requestBuilder = post("/single/customers/signIn");
        requestBuilder.sessionAttr("ROLE", "ADMIN")
                .param("email", "email")
                .param("password", "password")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED);

        String result = getContent(requestBuilder);
        assertEquals(user.toString(), result);
    }

    @Test
    public void testSignInFileClassMultipleAnnotationAdmin() {
        MockUser user = MockClasses.getUserMock();
        user.setId(null);
        user.setPassword(null);

        //Build mock request
        MockHttpServletRequestBuilder requestBuilder = post("/multiple/customers/signIn");
        requestBuilder.sessionAttr("ROLE", "ADMIN")
                .param("email", "email")
                .param("password", "password")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED);

        String result = getContent(requestBuilder);
        assertEquals(user.toString(), result);
    }


}
