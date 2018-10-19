package com.json.ignore.filter.file;

import mock.MockClasses;
import mock.MockHttpRequest;
import mock.MockMethods;
import mock.MockUser;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.server.ServletServerHttpRequest;

import static org.junit.Assert.*;

public class FileFilterTest {
    private ServletServerHttpRequest request;
    private MethodParameter methodParameter;
    private MockUser defaultMockUser;

    @Before
    public void init() {
        request = MockHttpRequest.getMockAdminRequest();
        methodParameter = MockMethods.findMethodParameterByName("fileAnnotation");
        assertNotNull(methodParameter);

        defaultMockUser = MockClasses.getUserMock();
        assertNotNull(defaultMockUser);
    }

    @Test
    public void testRequest() {
        MockUser user = MockClasses.getUserMock();
        FileFilter filter = new FileFilter(request, methodParameter);
        assertNotNull(filter);
    }

    @Test
    public void testSession() {
        FileFilter filter = new FileFilter(request.getServletRequest().getSession(), methodParameter);
        assertNotNull(filter);
    }

    @Test
    public void testFilterEqual() {
        MockUser user = MockClasses.getUserMock();
        FileFilter filter = new FileFilter(request, methodParameter);
        filter.filter(user);
        assertEquals(defaultMockUser, user);
    }

    @Test
    public void testFilterNotEqual() {
        MockUser user = MockClasses.getUserMock();
        FileFilter filter = new FileFilter(request, methodParameter);
        //Change class name where method is exists, just for test
        filter.setControllerClass(FileFilterTest.class);
        filter.filter(user);
        assertNotEquals(defaultMockUser, user);
    }

    @Test
    public void testFilterNullObject() {
        FileFilter filter = new FileFilter(request, methodParameter);
        //Change class name where method is exists, just for test
        filter.setControllerClass(FileFilterTest.class);
        filter.filter(null);
        assertNotNull(filter.getControllerClass());
    }
}
