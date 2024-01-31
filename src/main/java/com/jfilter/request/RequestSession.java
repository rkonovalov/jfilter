package com.jfilter.request;

import org.springframework.http.server.ServerHttpRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.util.StringUtils;
import java.util.Enumeration;
import java.util.Objects;

/**
 * Session request class
 * <p>
 * This is request class used to help working with Http Session
 */
public class RequestSession {
    private final HttpSession session;
    private final HttpServletRequest request;


    /**
     * Creates a new instance of the {@link RequestSession} class.
     *
     * @param request {@link HttpServletRequest}
     * @throws IllegalArgumentException if  request is null.
     */
    public RequestSession(HttpServletRequest request) {
        if (request == null)
            throw new IllegalArgumentException();
        this.request = request;
        this.session = request.getSession();
    }

    /**
     * Return session extracted from {@link ServerHttpRequest}
     *
     * @return {@link HttpSession}
     */
    public HttpSession getSession() {
        return session;
    }

    /**
     * Return initial request {@link HttpServletRequest}
     *
     * @return {@link HttpServletRequest}
     */
    public HttpServletRequest getRequest() {
        return request;
    }

    private boolean hasSessionProperty(String propertyName) {
        Enumeration<String> e = session.getAttributeNames();

        while (e.hasMoreElements()) {
            String attribute = e.nextElement();
            if (attribute.equals(propertyName))
                return true;
        }
        return false;
    }

    /**
     * Get attribute value from session attributes
     *
     * @param attributeName {@link String} attribute name
     * @return {@link Object} attribute value if exists, else null
     */
    public Object getSessionProperty(String attributeName) {
        return attributeName != null ? session.getAttribute(attributeName) : null;
    }

    /**
     * Check if session properties has property
     * <p>
     * Find property with name and value specified in method params
     *
     * @param attributeName  {@link String} attribute name
     * @param attributeValue {@link String} expected attribute value
     * @return {@link Boolean} true if property with name and value is exist, else false
     */
    public boolean isSessionPropertyExists(String attributeName, String attributeValue) {
        if (attributeName == null && attributeValue == null) {
            return false;
        } else if (StringUtils.isEmpty(attributeName) && StringUtils.isEmpty(attributeValue)) {
            return true;
        } else if (hasSessionProperty(attributeName)) {
            Object sessionObject = getSessionProperty(attributeName);
            return Objects.equals(sessionObject, attributeValue);
        } else
            return false;
    }
}
