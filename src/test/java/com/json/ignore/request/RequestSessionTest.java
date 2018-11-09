package com.json.ignore.request;

import com.json.ignore.mock.MockHttpRequest;
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
    public void testPropertyExist() {
        RequestSession requestSession = new RequestSession(MockHttpRequest.getMockAdminRequest());
        boolean result = requestSession.isSessionPropertyExists("ROLE", "ADMIN");

        assertTrue(result);
    }

    @Test
    public void testPropertyNotExist() {
        RequestSession requestSession = new RequestSession(MockHttpRequest.getMockAdminRequest());
        boolean result = requestSession.isSessionPropertyExists("UN_EXIST", "UN_EXIST");

        assertFalse(result);
    }

    @Test
    public void testPropertyNotExistNullProperty() {
        RequestSession requestSession = new RequestSession(MockHttpRequest.getMockAdminRequest());
        boolean result = requestSession.isSessionPropertyExists(null, "UN_EXIST");
        assertFalse(result);
    }

    @Test
    public void testPropertyNotExistNullValue() {
        RequestSession requestSession = new RequestSession(MockHttpRequest.getMockAdminRequest());
        boolean result = requestSession.isSessionPropertyExists("UN_EXIST", null);
        assertFalse(result);
    }

    @Test
    public void testPropertyNotExistNulls() {
        RequestSession requestSession = new RequestSession(MockHttpRequest.getMockAdminRequest());
        boolean result = requestSession.isSessionPropertyExists(null, null);
        assertFalse(result);
    }

    @Test
    public void testGetSessionProperty() {
        RequestSession requestSession = new RequestSession(MockHttpRequest.getMockAdminRequest());
        Object result = requestSession.getSessionProperty("ROLE");
        assertNotNull(result);
    }

    @Test
    public void testGetSessionPropertyFalse() {
        RequestSession requestSession = new RequestSession(MockHttpRequest.getMockAdminRequest());
        Object result = requestSession.getSessionProperty("UN_EXIST");
        assertNull(result);
    }

    @Test
    public void testGetSessionPropertyNull() {
        RequestSession requestSession = new RequestSession(MockHttpRequest.getMockAdminRequest());
        Object result = requestSession.getSessionProperty(null);
        assertNull(result);
    }


}
