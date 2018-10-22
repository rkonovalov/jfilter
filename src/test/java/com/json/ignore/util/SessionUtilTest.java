package com.json.ignore.util;

import com.json.ignore.mock.MockHttpRequest;
import org.junit.Test;
import org.springframework.http.server.ServletServerHttpRequest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class SessionUtilTest {

    @Test
    public void testClassExists() {
        ServletServerHttpRequest request = MockHttpRequest.getMockAdminRequest();
        assertNotNull(request);
        Object object = SessionUtil.getSessionProperty(request.getServletRequest().getSession(), "ROLE");
        assertEquals("ADMIN", object);
    }

    @Test
    public void testClassExistsFalse() {
        Object object = SessionUtil.getSessionProperty(null, "ROLE");
        assertNull(object);
    }
}
