package com.json.ignore.filter.file;

import com.json.ignore.mock.MockClasses;
import com.json.ignore.mock.MockHttpRequest;
import com.json.ignore.mock.MockMethods;
import com.json.ignore.mock.MockUser;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
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
    public void testFilteredObjectEqual() {
        MockUser user = MockClasses.getUserMock();
        FileFilter filter = new FileFilter(methodParameter);
        filter.setControllerClass(FileFilterTest.class);
        filter.filter(user, request);
        assertEquals(defaultMockUser, user);
    }

    @Test
    public void testFilteredObjectNotEqual() {
        MockUser user = MockClasses.getUserMock();
        FileFilter filter = new FileFilter(methodParameter);
        filter.filter(user, request);
        assertNotEquals(defaultMockUser, user);
    }

    @Test
    public void testFilterNullObject() {
        FileFilter filter = new FileFilter(methodParameter);
        filter.filter(null, request);
        assertNotNull(filter.getControllerClass());
    }

    @Test
    public void testFilterNoControllers() {

        FileFilter filter = new FileFilter(fileAnnotationNoControllers);
        filter.filter(null, request);
        assertNotNull(filter.getControllerClass());

    }

    @Test
    public void testFilterNoStrategies() {
        FileFilter filter = new FileFilter(fileAnnotationNoStrategies);
        filter.filter(null, request);
        assertNotNull(filter.getControllerClass());
    }

    @Test
    public void testFilterClassesDuplicated() {
        MockUser user = MockClasses.getUserMock();
        assertNotNull(user);

        FileFilter filter = new FileFilter(fileAnnotationClassDuplicated);
        filter.filter(user, request);
        assertNotNull(filter.getControllerClass());
    }

    @Test(expected = NullPointerException.class)
    public void testNotExistFile() {
        MockUser user = MockClasses.getUserMock();

        FileFilter fileFilter = new FileFilter(MockMethods.methodWithoutAnnotations());
        fileFilter.filter(user, MockHttpRequest.getMockAdminRequest());

        assertEquals(MockClasses.getUserMock(), user);
    }
}
