package com.json.ignore.filter.file;

import com.json.ignore.FileIOException;
import com.json.ignore.filter.BaseFilter;
import com.json.ignore.filter.FilterFactory;
import mock.MockClasses;
import mock.MockHttpRequest;
import mock.MockMethods;
import mock.MockUser;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.server.ServletServerHttpRequest;

import java.io.File;

import static org.junit.Assert.assertNotNull;

public class FileFilterTest {
    private ServletServerHttpRequest request;


    @Before
    public void init() {
        request = MockHttpRequest.getMockAdminRequest();
    }

    @Test
    public void testFileFilter() {
        MockUser user = MockClasses.getUserMock();
        MethodParameter methodParameter = MockMethods.findMethodParameterByName("fileAnnotation");
        assertNotNull(methodParameter);
        FileFilter filter = new FileFilter(request, methodParameter);
        filter.filter(user);
        assertNotNull(filter);
    }

    @Test
    public void testSession() {
        MethodParameter methodParameter = MockMethods.findMethodParameterByName("fileAnnotation");
        assertNotNull(methodParameter);
        FileFilter filter = new FileFilter(request.getServletRequest().getSession(), methodParameter);
        assertNotNull(filter);
    }
}
