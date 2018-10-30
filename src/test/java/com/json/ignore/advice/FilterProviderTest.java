package com.json.ignore.advice;

import com.json.ignore.mock.MockClasses;
import com.json.ignore.mock.MockHttpRequest;
import com.json.ignore.mock.MockMethods;
import com.json.ignore.mock.MockUser;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.server.ServletServerHttpRequest;

import java.io.Serializable;

import static org.junit.Assert.*;

public class FilterProviderTest {
    private FilterProvider<Serializable> filterProvider;
    private MockUser defaultUser;
    private ServletServerHttpRequest defaultRequest;
    private MethodParameter fileAnnotationMethod;
    private MethodParameter methodWithoutAnnotationsMethod;

    @Before
    public void init() {
        filterProvider = new FilterProvider<>();
        defaultUser = MockClasses.getUserMock();
        assertNotNull(defaultUser);

        defaultRequest = MockHttpRequest.getMockAdminRequest();
        assertNotNull(defaultRequest);

        fileAnnotationMethod = MockMethods.fileAnnotation();
        assertNotNull(fileAnnotationMethod);

        methodWithoutAnnotationsMethod = MockMethods.methodWithoutAnnotations();
        assertNotNull(methodWithoutAnnotationsMethod);
    }

    @Test
    public void testIsAcceptFalse() {
        boolean result = filterProvider.isAccept(methodWithoutAnnotationsMethod);
        assertFalse(result);
    }
}
