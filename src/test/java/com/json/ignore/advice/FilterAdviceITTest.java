package com.json.ignore.advice;

import com.json.ignore.mock.MockClasses;
import com.json.ignore.mock.MockHttpRequest;
import com.json.ignore.mock.MockUser;
import com.json.ignore.mock.config.WSConfigurationEnabled;
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

import static com.json.ignore.mock.webservice.WSClassFile.MAPPING_SIGN_IN_FILE;
import static com.json.ignore.mock.webservice.WSMethod.*;
import static junit.framework.TestCase.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ContextConfiguration(classes = WSConfigurationEnabled.class)
@RunWith(SpringRunner.class)
@WebAppConfiguration("src/main/resources")
public class FilterAdviceITTest {
    private MockMvc mockMvc;
    private WebApplicationContext webApplicationContext;

    @Autowired
    public FilterAdviceITTest setWebApplicationContext(WebApplicationContext webApplicationContext) {
        this.webApplicationContext = webApplicationContext;
        return this;
    }

    @Before
    public void init() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void testWebContext() {
        ServletContext servletContext = webApplicationContext.getServletContext();
        Assert.assertNotNull(servletContext);
    }

    @Test
    public void testWebContextIsMockServletContext() {
        ServletContext servletContext = webApplicationContext.getServletContext();
        Assert.assertNotNull(servletContext);
        Assert.assertTrue(servletContext instanceof MockServletContext);
    }

    @Test
    public void testSignInSingleAnnotation() {
        MockUser user = MockClasses.getUserMock();
        user.setId(null);
        user.setPassword(null);

        //Build mock request
        MockHttpServletRequestBuilder requestBuilder = post(MAPPING_SIGN_IN_SINGLE_ANNOTATION);
        requestBuilder.param("email", "email")
                .param("password", "password")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED);

        String result = MockHttpRequest.getContent(mockMvc, requestBuilder);
        assertEquals(user.toString(), result);
    }

    @Test
    public void testSignInFileAnnotationAdmin() {
        MockUser user = MockClasses.getUserMock();
        user.setId(null);

        //Build mock request
        MockHttpServletRequestBuilder requestBuilder = post(MAPPING_SIGN_IN_FILE_ANNOTATION);
        requestBuilder.sessionAttr("ROLE", "ADMIN")
                .param("email", "email")
                .param("password", "password")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED);

        String result = MockHttpRequest.getContent(mockMvc, requestBuilder);
        assertEquals(user.toString(), result);
    }

    @Test
    public void testSignInUnExistedFile() {
        MockUser user = MockClasses.getUserMock();

        //Build mock request
        MockHttpServletRequestBuilder requestBuilder = post(MAPPING_SIGN_IN_UN_EXIST_FILE);
        requestBuilder.sessionAttr("ROLE", "ADMIN")
                .param("email", "email")
                .param("password", "password")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED);

        String result = MockHttpRequest.getContent(mockMvc, requestBuilder);
        assertEquals(user.toString(), result);
    }

@Test
    public void testSignInDefaultStrategyFile() {
        MockUser user = MockClasses.getUserMock();
        user.setId(null);
        user.setEmail(null);
        user.setPassword(null);

        //Build mock request
        MockHttpServletRequestBuilder requestBuilder = post(MAPPING_SIGN_IN_FILE_DEFAULT_STRATEGY);
        requestBuilder
                .param("email", "email")
                .param("password", "password")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED);

        String result = MockHttpRequest.getContent(mockMvc, requestBuilder);
        assertEquals(user.toString(), result);
    }

    @Test
    public void testSignInFileAnnotationUser() {
        MockUser user = MockClasses.getUserMock();
        user.setId(null);
        user.setPassword(null);

        //Build mock request
        MockHttpServletRequestBuilder requestBuilder = post(MAPPING_SIGN_IN_FILE_ANNOTATION);
        requestBuilder.sessionAttr("ROLE", "USER")
                .param("email", "email")
                .param("password", "password")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED);

        String result = MockHttpRequest.getContent(mockMvc, requestBuilder);
        assertEquals(user.toString(), result);


    }

    @Test
    public void testSignInStrategyAnnotation() {
        MockUser user = MockClasses.getUserMock();
        user.setId(null);
        user.setPassword(null);

        //Build mock request
        MockHttpServletRequestBuilder requestBuilder = post(MAPPING_SIGN_IN_STRATEGY_ANNOTATION);
        requestBuilder.sessionAttr("ROLE", "USER")
                .param("email", "email")
                .param("password", "password")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED);

        String result = MockHttpRequest.getContent(mockMvc, requestBuilder);
        assertEquals(user.toString(), result);
    }

 @Test
    public void testSignInStrategyDefault() {
        MockUser user = MockClasses.getUserMock();
        user.setId(null);
        user.setPassword(null);
        user.setEmail(null);

        //Build mock request
        MockHttpServletRequestBuilder requestBuilder = post(MAPPING_SIGN_IN_STRATEGY_DEFAULT);
        requestBuilder
                .param("email", "email")
                .param("password", "password")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED);

        String result = MockHttpRequest.getContent(mockMvc, requestBuilder);
        assertEquals(user.toString(), result);
    }


    @Test
    public void testSignInFileClassAnnotationAdmin() {
        MockUser user = MockClasses.getUserMock();
        user.setId(null);

        //Build mock request
        MockHttpServletRequestBuilder requestBuilder = post(MAPPING_SIGN_IN_FILE);
        requestBuilder.sessionAttr("ROLE", "ADMIN")
                .param("email", "email")
                .param("password", "password")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED);

        String result = MockHttpRequest.getContent(mockMvc, requestBuilder);
        assertEquals(user.toString(), result);
    }
}
