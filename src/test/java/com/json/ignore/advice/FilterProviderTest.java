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
    private MethodParameter fileAnnotationMethod;
    private MethodParameter methodWithoutAnnotationsMethod;
    private MethodParameter singleAnnotationMethod;

    @Before
    public void init() {
        filterProvider = new FilterProvider();
        defaultUser = MockClasses.getUserMock();
        assertNotNull(defaultUser);

        defaultRequest = MockHttpRequest.getMockAdminRequest();
        assertNotNull(defaultRequest);

        fileAnnotationMethod = MockMethods.fileAnnotation();
        assertNotNull(fileAnnotationMethod);

        methodWithoutAnnotationsMethod = MockMethods.methodWithoutAnnotations();
        assertNotNull(methodWithoutAnnotationsMethod);

        singleAnnotationMethod = MockMethods.singleAnnotation();
        assertNotNull(singleAnnotationMethod);
    }

    @Test
    public void testIsAccept() {
        boolean result = filterProvider.isAccept(singleAnnotationMethod);
        assertTrue(result);
    }

    @Test
    public void testIsAcceptFalse() {
        boolean result = filterProvider.isAccept(methodWithoutAnnotationsMethod);
        assertFalse(result);
    }

    @Test
    public void testFilter() {
        MockUser user = MockClasses.getUserMock();
        assertNotNull(user);


        filterProvider.filter(defaultRequest, fileAnnotationMethod, user);
        assertNotEquals(defaultUser, user);
    }

    @Test
    public void testFilterFalse() {
        MockUser user = MockClasses.getUserMock();
        assertNotNull(user);

        filterProvider.filter(defaultRequest, methodWithoutAnnotationsMethod, user);
        assertEquals(defaultUser, user);
    }

    @Test
    public void testFilterNull() {
        Object result = filterProvider.filter(defaultRequest, fileAnnotationMethod, null);
        assertNull(result);
    }

    @Test
    public void testFilterTwice() {
        MockUser user = MockClasses.getUserMock();
        MockUser user2 = MockClasses.getUserMock();
        assertNotNull(user);
        assertNotNull(user2);

        user = (MockUser) filterProvider.filter(defaultRequest, fileAnnotationMethod, user);
        user2 = (MockUser) filterProvider.filter(defaultRequest, fileAnnotationMethod, user2);

        assertEquals(user, user2);
    }

    @Test
    public void testFilterRoleAdmin() {
        MockUser user = MockClasses.getUserMock();
        assertNotNull(user);

        user = (MockUser) filterProvider.filter(defaultRequest, fileAnnotationMethod, user);
        boolean result = user.getId() == null && user.getPassword() != null;

        assertTrue(result);
    }

    @Test
    public void testFilterRoleUser() {
        MockUser user = MockClasses.getUserMock();
        assertNotNull(user);

        defaultRequest.getServletRequest().getSession().setAttribute("ROLE", "USER");

        user = (MockUser) filterProvider.filter(defaultRequest, fileAnnotationMethod, user);

        boolean result = user.getId() == null && user.getPassword() == null;

        assertTrue(result);
    }

    @Test
    public void testFilterRoleNull() {
        MockUser user = MockClasses.getUserMock();
        assertNotNull(user);

        defaultRequest.getServletRequest().getSession().setAttribute("ROLE", null);

        user = (MockUser) filterProvider.filter(defaultRequest, fileAnnotationMethod, user);

        boolean result = user.getId() != null && user.getPassword() != null;

        assertTrue(result);
    }

    @Test
    public void testCacheSize1() {
        filterProvider.clearCache();

        MockUser user = MockClasses.getUserMock();
        MockUser user2 = MockClasses.getUserMock();

        filterProvider.filter(defaultRequest, MockMethods.singleAnnotation(), user);
        filterProvider.filter(defaultRequest, MockMethods.secondSingleAnnotation(), user2);

        assertEquals(1, filterProvider.cacheSize());

    }

    @Test
    public void testCacheSize2() {
        filterProvider.clearCache();

        filterProvider.filter(defaultRequest, MockMethods.singleAnnotation(), defaultUser);
        filterProvider.filter(defaultRequest, MockMethods.secondSingleAnnotation(), defaultUser);
        filterProvider.filter(defaultRequest, MockMethods.thirdSingleAnnotation(), defaultUser);

        filterProvider.filter(defaultRequest, MockMethods.singleAnnotation(), defaultUser);
        filterProvider.filter(defaultRequest, MockMethods.secondSingleAnnotation(), defaultUser);
        filterProvider.filter(defaultRequest, MockMethods.thirdSingleAnnotation(), defaultUser);

        assertEquals(2, filterProvider.cacheSize());

    }


}
