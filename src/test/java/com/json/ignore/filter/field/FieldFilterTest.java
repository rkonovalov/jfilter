package com.json.ignore.filter.field;

import mock.MockClasses;
import mock.MockHttpRequest;
import mock.MockMethods;
import mock.MockUser;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.server.ServletServerHttpRequest;
import javax.servlet.http.HttpSession;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class FieldFilterTest {
    private HttpSession session;
    private ServletServerHttpRequest serverHttpRequest;
    private MethodParameter methodParameter;
    private MockUser defaultMockUser;

    @Before
    public void initSession() {
        serverHttpRequest = MockHttpRequest.getMockAdminRequest();
        assertNotNull(serverHttpRequest);

        session = serverHttpRequest.getServletRequest().getSession();
        assertNotNull(session);

        methodParameter = MockMethods.singleAnnotation();
        assertNotNull(methodParameter);

        defaultMockUser = MockClasses.getUserMock();
        assertNotNull(defaultMockUser);
    }

    @Test
    public void testFieldFilterWithRequest() {
        MockUser user = MockClasses.getUserMock();
        FieldFilter fieldFilter = new FieldFilter(serverHttpRequest, methodParameter);
        fieldFilter.filter(user);
        assertNotEquals(user, defaultMockUser);
    }

    @Test
    public void testFieldFilterWithSession() {
        MockUser user = new MockUser();
        FieldFilter fieldFilter = new FieldFilter(session, methodParameter);
        fieldFilter.filter(user);
        assertNotEquals(user, defaultMockUser);
    }
}
