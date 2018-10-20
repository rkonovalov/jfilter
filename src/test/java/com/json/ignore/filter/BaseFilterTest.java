package com.json.ignore.filter;


import com.json.ignore.filter.field.FieldFilter;
import mock.MockHttpRequest;
import mock.MockMethods;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.server.ServletServerHttpRequest;

import javax.servlet.http.HttpSession;

import static org.junit.Assert.*;

public class BaseFilterTest {
    private ServletServerHttpRequest request;
    private MethodParameter methodParameter;

    @Before
    public void init() {
        request = MockHttpRequest.getMockAdminRequest();
        methodParameter = MockMethods.singleAnnotation();
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
        BaseFilter baseFilter = new FieldFilter(request.getServletRequest().getSession(), methodParameter);
        boolean result = baseFilter.isSessionPropertyExists("ROLE", "SOME VALUE");
        assertFalse(result);
    }

    @Test
    public void testNullRequest() {
        BaseFilter baseFilter = new FieldFilter((ServletServerHttpRequest) null, methodParameter);
        boolean result = baseFilter.isSessionPropertyExists("ROLE", "ADMIN");
        assertFalse(result);
    }

    @Test
    public void testNullSession() {
        BaseFilter baseFilter = new FieldFilter((HttpSession) null, methodParameter);
        boolean result = baseFilter.isSessionPropertyExists("ROLE", "ADMIN");
        assertFalse(result);
    }

    @Test(expected = NullPointerException.class)
    public void testNullMethod() {
        BaseFilter baseFilter = new FieldFilter(request, null);
        assertNotNull(baseFilter);
    }

    @Test(expected = NullPointerException.class)
    public void testNullAllParameters() {
        BaseFilter baseFilter = new FieldFilter((HttpSession) null, null);
        assertNotNull(baseFilter);
    }
}
