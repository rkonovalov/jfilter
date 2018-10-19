package com.json.ignore.filter;


import com.json.ignore.filter.field.FieldFilter;
import mock.MockHttpRequest;
import mock.MockMethods;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.server.ServletServerHttpRequest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class BaseFilterTest {
    private ServletServerHttpRequest request;
    private MethodParameter methodParameter;

    @Before
    public void init() {
        request = MockHttpRequest.getMockAdminRequest();
        methodParameter = MockMethods.findMethodParameterByName("singleAnnotation");
        assertNotNull(methodParameter);
    }


    @Test
    public void testSessionAttributeExists() {
        BaseFilter baseFilter = new FieldFilter(request, methodParameter);
        boolean result = baseFilter.isSessionPropertyExists("ROLE", "ADMIN");
        assertTrue(result);
    }

    @Test
    public void testSessionAttributeNotExists() {
        BaseFilter baseFilter = new FieldFilter(request, methodParameter);
        boolean result = baseFilter.isSessionPropertyExists("ROLE", "SOME VALUE");
        assertFalse(result);
    }
}
