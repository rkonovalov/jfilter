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
    private MethodParameter fileAnnotationNoControllers;
    private MethodParameter fileAnnotationNoStrategies;
    private MethodParameter fileAnnotationClassDuplicated;
    private MockUser defaultMockUser;

    @Before
    public void init() {
        request = MockHttpRequest.getMockAdminRequest();

        methodParameter = MockMethods.fileAnnotation();
        assertNotNull(methodParameter);

        fileAnnotationNoControllers = MockMethods.fileAnnotationNoControllers();
        assertNotNull(fileAnnotationNoControllers);

        fileAnnotationNoStrategies = MockMethods.fileAnnotationNoStrategies();
        assertNotNull(fileAnnotationNoStrategies);

        fileAnnotationClassDuplicated = MockMethods.fileAnnotationClassDuplicated();
        assertNotNull(fileAnnotationClassDuplicated);

        defaultMockUser = MockClasses.getUserMock();
        assertNotNull(defaultMockUser);
    }

    @Test
    public void testRequest() {
        FileFilter filter = new FileFilter(request, methodParameter);
        assertNotNull(filter);
    }

    @Test
    public void testSession() {
        FileFilter filter = new FileFilter(request.getServletRequest().getSession(), methodParameter);
        assertNotNull(filter);
    }

    @Test
    public void testFilteredObjectEqual() {
        MockUser user = MockClasses.getUserMock();
        FileFilter filter = new FileFilter(request, methodParameter);
        filter.setControllerClass(FileFilterTest.class);
        filter.filter(user);
        assertEquals(defaultMockUser, user);
    }

    @Test
    public void testFilteredObjectNotEqual() {
        MockUser user = MockClasses.getUserMock();
        FileFilter filter = new FileFilter(request, methodParameter);
        filter.filter(user);
        assertNotEquals(defaultMockUser, user);
    }

    @Test
    public void testFilterNullObject() {
        FileFilter filter = new FileFilter(request, methodParameter);
        filter.filter(null);
        assertNotNull(filter.getControllerClass());
    }

    @Test
    public void testFilterNoControllers() {

        FileFilter filter = new FileFilter(request, fileAnnotationNoControllers);
        filter.filter(null);
        assertNotNull(filter.getControllerClass());

    }

    @Test
    public void testFilterNoStrategies() {
        FileFilter filter = new FileFilter(request, fileAnnotationNoStrategies);
        filter.filter(null);
        assertNotNull(filter.getControllerClass());
    }

    @Test
    public void testFilterClassesDuplicated() {
        MockUser user = MockClasses.getUserMock();
        assertNotNull(user);

        FileFilter filter = new FileFilter(request, fileAnnotationClassDuplicated);
        filter.filter(user);
        assertNotNull(filter.getControllerClass());
    }
}
