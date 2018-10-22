package com.json.ignore.filter;


import com.json.ignore.filter.field.FieldFilter;
import com.json.ignore.mock.MockHttpRequest;
import com.json.ignore.mock.MockMethods;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.server.ServletServerHttpRequest;
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

    @Test(expected = NullPointerException.class)
    public void testNullMethod() {
        BaseFilter baseFilter = new FieldFilter( null);
        assertNotNull(baseFilter);
    }
}
