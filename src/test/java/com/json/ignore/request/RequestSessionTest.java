package com.json.ignore.request;

import com.json.ignore.mock.MockHttpRequest;
import com.json.ignore.mock.MockMethods;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

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

    @Test
    public void testisPropertyExist() {
        RequestSession requestSession = new RequestSession(MockHttpRequest.getMockAdminRequest());
        boolean result = requestSession.isSessionPropertyExists("ROLE", "ADMIN");

        assertTrue(result);
    }

    @Test
    public void testisPropertyNotExist() {
        RequestSession requestSession = new RequestSession(MockHttpRequest.getMockAdminRequest());
        boolean result = requestSession.isSessionPropertyExists("UN_EXIST", "UN_EXIST");

        assertFalse(result);
    }

    @Test
    public void testisPropertyNotExistNull() {
        RequestSession requestSession = new RequestSession(MockHttpRequest.getMockAdminRequest());
        boolean result = requestSession.isSessionPropertyExists("UN_EXIST", null);
        assertFalse(result);
    }

    @Test
    public void testisPropertyNotExistNulls() {
        RequestSession requestSession = new RequestSession(MockHttpRequest.getMockAdminRequest());
        boolean result = requestSession.isSessionPropertyExists(null, null);
        assertFalse(result);
    }


}
