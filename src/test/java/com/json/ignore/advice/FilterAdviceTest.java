package com.json.ignore.advice;

import com.json.ignore.mock.MockHttpRequest;
import com.json.ignore.mock.MockMethods;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.mock.web.MockHttpServletResponse;
import static org.junit.Assert.*;

public class FilterAdviceTest {
    private FilterAdvice filterAdvice;
    private ServerHttpResponse response;
    private ServletServerHttpRequest request;

    @Before
    public void init() {
        filterAdvice = new FilterAdvice();
        filterAdvice.setFilterProvider(new FilterProvider());

        response = new ServletServerHttpResponse(new MockHttpServletResponse());
        request = MockHttpRequest.getMockAdminRequest();
    }

    @Test
    public void testSupportsFalse() {
        boolean result = filterAdvice.supports(MockMethods.methodWithoutAnnotations(), null);
        assertFalse(result);
    }
}
