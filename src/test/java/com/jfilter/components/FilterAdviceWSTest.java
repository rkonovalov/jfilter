package com.jfilter.components;

import com.jfilter.mock.MockClasses;
import com.jfilter.mock.webservice.WSClassFile;
import com.jfilter.mock.MockHttpRequest;
import com.jfilter.mock.MockUser;
import com.jfilter.mock.config.WSConfigurationEnabled;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import javax.servlet.ServletContext;

import static com.jfilter.mock.webservice.WSMethod.*;
import static junit.framework.TestCase.assertEquals;

@ContextConfiguration(classes = WSConfigurationEnabled.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class FilterAdviceWSTest {
    private MockMvc mockMvc;
    private WebApplicationContext webApplicationContext;

    @Autowired
    public FilterAdviceWSTest setWebApplicationContext(WebApplicationContext webApplicationContext) {
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

        String result = MockHttpRequest.doRequest(mockMvc, MAPPING_SIGN_IN_SINGLE_ANNOTATION, null, null);

        assertEquals(user.toString(), result);
    }

    @Test
    public void testSignInSingleAnnotationXml() {
        MockUser user = MockClasses.getUserMock();
        user.setId(null);
        user.setPassword(null);

        String result = MockHttpRequest.doRequest(mockMvc, MAPPING_SIGN_IN_SINGLE_ANNOTATION_XML, null, null);

        assertEquals(user.toXmlString(), result);
    }

    @Test
    public void testSignInFileAnnotationAdmin() {
        MockUser user = MockClasses.getUserMock();
        user.setId(null);

        String result = MockHttpRequest.doRequest(mockMvc, MAPPING_SIGN_IN_FILE_ANNOTATION, "ROLE", "ADMIN");

        assertEquals(user.toString(), result);
    }

    @Test
    public void testSignInUnExistedFile() {
        MockUser user = MockClasses.getUserMock();

        String result = MockHttpRequest.doRequest(mockMvc, MAPPING_SIGN_IN_UN_EXIST_FILE, null, null);

        assertEquals(user.toString(), result);
    }

    @Test
    public void testSignInDefaultStrategyFile() {
        MockUser user = MockClasses.getUserMock();
        user.setId(null);
        user.setEmail(null);
        user.setPassword(null);

        String result = MockHttpRequest.doRequest(mockMvc, MAPPING_SIGN_IN_FILE_DEFAULT_STRATEGY, null, null);

        assertEquals(user.toString(), result);
    }

    @Test
    public void testSignInFileAnnotationUser() {
        MockUser user = MockClasses.getUserMock();
        user.setId(null);
        user.setPassword(null);

        String result = MockHttpRequest.doRequest(mockMvc, MAPPING_SIGN_IN_FILE_ANNOTATION, "ROLE", "USER");

        assertEquals(user.toString(), result);
    }

    @Test
    public void testSignInStrategyDefault() {
        MockUser user = MockClasses.getUserMock();
        user.setId(null);
        user.setPassword(null);
        user.setEmail(null);

        String result = MockHttpRequest.doRequest(mockMvc, MAPPING_SIGN_IN_STRATEGY_DEFAULT, null, null);

        assertEquals(user.toString(), result);
    }

    @Test
    public void testSignInFileClassAnnotationAdmin() {
        MockUser user = MockClasses.getUserMock();
        user.setId(null);

        String result = MockHttpRequest.doRequest(mockMvc, WSClassFile.MAPPING_SIGN_IN_FILE, "ROLE", "ADMIN");

        assertEquals(user.toString(), result);
    }

    @Test
    public void testSignInWithoutProduce() {
        MockUser user = MockClasses.getUserMock();
        user.setId(null);
        user.setPassword(null);

        String result = MockHttpRequest.doRequest(mockMvc, MAPPING_SIGN_IN_WITHOUT_PRODUCE, null, null);

        assertEquals(user.toXmlString(), result);
    }
}
