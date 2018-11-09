package com.json.ignore.request;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.Objects;

/**
 * Session request class
 * <p>
 * This is request class used to help working with Http Session
 */
public class RequestSession {
    private final HttpSession session;
    private final ServletServerHttpRequest request;

    /**
     * Creates a new instance of the {@link RequestSession} class.
     *
     * @param request {@link ServerHttpRequest}
     */
    public RequestSession(ServerHttpRequest request) {
        this.request = getRequest(request);
        this.session = this.request.getServletRequest().getSession();
    }

    /**
     * Return session extracted from {@link ServerHttpRequest}
     *
     * @return {@link HttpSession}
     */
    public HttpSession getSession() {
        return session;
    }

    private ServletServerHttpRequest getRequest(ServerHttpRequest serverHttpRequest) {
        if (serverHttpRequest instanceof ServletServerHttpRequest) {
            return (ServletServerHttpRequest) serverHttpRequest;
        } else
            throw new IllegalArgumentException();
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
        } else
            if (StringUtils.isEmpty(attributeName) && StringUtils.isEmpty(attributeValue)) {
            return true;
        } else if (hasSessionProperty(attributeName)) {
            Object sessionObject = getSessionProperty(attributeName);
            return Objects.equals(sessionObject, attributeValue);
        } else
            return false;

        /*Object sessionObject = getSessionProperty(attributeName);
        boolean defaultValue = StringUtils.isEmpty(attributeName) && StringUtils.isEmpty(attributeValue);
        return (sessionObject != null && Objects.equals(sessionObject, attributeValue)) || defaultValue;*/
    }
}
