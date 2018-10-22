package com.json.ignore.filter.field;

import com.json.ignore.mock.MockClasses;
import com.json.ignore.mock.MockHttpRequest;
import com.json.ignore.mock.MockMethods;
import com.json.ignore.mock.MockUser;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.server.ServletServerHttpRequest;
import javax.servlet.http.HttpSession;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class FieldFilterTest {
    private ServletServerHttpRequest serverHttpRequest;
    private MethodParameter methodParameter;
    private MockUser defaultMockUser;

    @Before
    public void initSession() {
        serverHttpRequest = MockHttpRequest.getMockAdminRequest();
        assertNotNull(serverHttpRequest);

        methodParameter = MockMethods.singleAnnotation();
        assertNotNull(methodParameter);

        defaultMockUser = MockClasses.getUserMock();
        assertNotNull(defaultMockUser);
    }

    @Test
    public void testFieldFilter() {
        MockUser user = MockClasses.getUserMock();
        FieldFilter fieldFilter = new FieldFilter(methodParameter);
        fieldFilter.filter(user, serverHttpRequest);
        assertNotEquals(user, defaultMockUser);
    }
}
