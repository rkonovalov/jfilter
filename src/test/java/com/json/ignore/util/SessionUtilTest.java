package com.json.ignore.util;

import com.json.ignore.mock.MockHttpRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.server.ServletServerHttpRequest;

import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class SessionUtilTest {
    private SessionUtil sessionUtil;

    @Before
    public void init() {
        this.sessionUtil = new SessionUtil(MockHttpRequest.getMockAdminRequest());
    }

    @Test
    public void testClassExists() {
        Object object = sessionUtil.getSessionProperty(sessionUtil.getSession(), "ROLE");
        assertEquals("ADMIN", object);
    }

    @Test
    public void testClassExistsFalse() {
        Object object = sessionUtil.getSessionProperty(sessionUtil.getSession(),"SOME_FIELD");
        assertNull(object);
    }
}
