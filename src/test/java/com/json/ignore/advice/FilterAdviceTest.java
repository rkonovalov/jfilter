package com.json.ignore.advice;

import com.json.ignore.mock.MockClasses;
import com.json.ignore.mock.MockHttpRequest;
import com.json.ignore.mock.MockMethods;
import com.json.ignore.mock.MockUser;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.http.server.*;
import org.springframework.mock.web.MockHttpServletResponse;
import static org.junit.Assert.*;

public class FilterAdviceTest {
    private FilterAdvice filterAdvice;
    private FilterProvider filterProvider;
    private ServerHttpResponse response;
    private ServletServerHttpRequest request;

    @Before
    public void init() {
        filterAdvice = new FilterAdvice();
        filterProvider = new FilterProvider();

        filterAdvice.setFilterProvider(filterProvider);

        response = new ServletServerHttpResponse(new MockHttpServletResponse());
        request = MockHttpRequest.getMockAdminRequest();
    }

    @Test
    public void testSupports() {
        boolean result = filterAdvice.supports(MockMethods.fileAnnotation(), null);
        assertTrue(result);

    }

    @Test
    public void testSupportsFalse() {
        boolean result = filterAdvice.supports(MockMethods.methodWithoutAnnotations(), null);
        assertFalse(result);
    }

    @Test
    public void testBeforeBodyWrite() {
        MockUser user = MockClasses.getUserMock();

        user = (MockUser) filterAdvice.beforeBodyWrite(user, MockMethods.singleAnnotation(), MediaType.APPLICATION_JSON,
                Jaxb2RootElementHttpMessageConverter.class, request, response);
        assertNotEquals(user, MockClasses.getUserMock());
    }

    @Test
    public void testBeforeBodyWriteFalse() {
        MockUser user = MockClasses.getUserMock();

        user = (MockUser) filterAdvice.beforeBodyWrite(user, MockMethods.methodWithoutAnnotations(), MediaType.APPLICATION_JSON,
                Jaxb2RootElementHttpMessageConverter.class, request, response);
        assertEquals(user, MockClasses.getUserMock());
    }
}
