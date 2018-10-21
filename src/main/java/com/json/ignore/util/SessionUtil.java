package com.json.ignore.util;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;

import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * Session util class
 * <p>
 * This is util class used to help working with Http Session
 */
public final class SessionUtil {

    protected SessionUtil() {
    }

    /**
     * Gets session from request
     * <p>
     * If request is instance of {@link ServletServerHttpRequest} returns session. Another way return null
     *
     * @param serverHttpRequest {@link ServerHttpRequest} http request
     * @return {@link HttpSession} session, else null
     */
    public static HttpSession getSession(ServerHttpRequest serverHttpRequest) {
        if (serverHttpRequest instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) serverHttpRequest;
            return servletRequest.getServletRequest().getSession();
        } else
            return null;
    }

    /**
     * Get attribute value from session attributes
     * @param session {@link HttpSession} session
     * @param attributeName {@link String} attribute name
     * @return {@link Object} attribute value if exists, else null
     */
    public static Object getSessionProperty(HttpSession session, String attributeName) {
        if (session != null) {
            return session.getAttribute(attributeName);
        } else
            return null;
    }

    /**
     * Check if session properties has property
     * <p>
     * Find property with name and value specified in method params
     * @param session {@link HttpSession} session
     * @param attributeName {@link String} attribute name
     * @param attributeValue {@link String} expected attribute value
     * @return {@link Boolean} true if property with name and value is exist, else false
     */
    public static boolean isSessionPropertyExists(HttpSession session, String attributeName, String attributeValue) {
        if (session != null) {
            Object sessionObject = getSessionProperty(session, attributeName);
            return Objects.equals(sessionObject, attributeValue);
        } else
            return false;
    }
}
