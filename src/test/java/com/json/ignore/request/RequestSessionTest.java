package com.json.ignore.request;

import com.json.ignore.mock.MockHttpRequest;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class RequestSessionTest {
    private RequestSession requestSession;

    @Before
    public void init() {
        this.requestSession = new RequestSession(MockHttpRequest.getMockAdminRequest());
    }

    @Test
    public void testClassExists() {
        Object object = requestSession.getSessionProperty("ROLE");
        assertEquals("ADMIN", object);
    }

    @Test
    public void testClassExistsFalse() {
        Object object = requestSession.getSessionProperty("SOME_FIELD");
        assertNull(object);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullSession() {
        RequestSession requestSession = new RequestSession(null);
        assertNotNull(requestSession);
    }

    @Test
    public void testGetSession() {
        assertNotNull(requestSession.getSession());
    }


}
