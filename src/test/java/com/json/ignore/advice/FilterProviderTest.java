package com.json.ignore.advice;

import mock.MockClasses;
import mock.MockHttpRequest;
import mock.MockMethods;
import mock.MockUser;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.server.ServletServerHttpRequest;

import static org.junit.Assert.*;

public class FilterProviderTest {
    private FilterProvider filterProvider;
    private MockUser defaultUser;
    private ServletServerHttpRequest defaultRequest;

    @Before
    public void init() {
        filterProvider = new FilterProvider();
        defaultUser = MockClasses.getUserMock();
        defaultRequest = MockHttpRequest.getMockAdminRequest();
    }

    @Test
    public void testIsAccept() {
        MethodParameter methodParameter = MockMethods.singleAnnotation();
        assertNotNull(methodParameter);

        boolean result = filterProvider.isAccept(methodParameter);
        assertTrue(result);
    }

    @Test
    public void testIsAcceptFalse() {
        MethodParameter methodParameter = MockMethods.methodWithoutAnnotations();
        assertNotNull(methodParameter);

        boolean result = filterProvider.isAccept(methodParameter);
        assertFalse(result);
    }

    @Test
    public void testFilter() {
        MockUser user = MockClasses.getUserMock();
        assertNotNull(user);

        MethodParameter methodParameter = MockMethods.fileAnnotation();
        assertNotNull(methodParameter);

        filterProvider.filter(defaultRequest, methodParameter, user);
        assertNotEquals(defaultUser, user);
    }

    @Test
    public void testFilterFalse() {
        MockUser user = MockClasses.getUserMock();
        assertNotNull(user);

        MethodParameter methodParameter = MockMethods.methodWithoutAnnotations();
        assertNotNull(methodParameter);

        filterProvider.filter(defaultRequest, methodParameter, user);
        assertEquals(defaultUser, user);
    }

    @Test
    public void testFilterNull() {
        MethodParameter methodParameter = MockMethods.fileAnnotation();
        assertNotNull(methodParameter);

        Object result = filterProvider.filter(defaultRequest, methodParameter, null);
        assertNull(result);
    }


}
