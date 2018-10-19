package com.json.ignore.filter;

import com.json.ignore.FieldAccessException;
import org.springframework.core.MethodParameter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;

import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * This class is base class strategy filtration
 */

public abstract class BaseFilter {
    /**
     * Http session, may be null
     */
    private HttpSession session;

    /**
     * Default constructor
     *
     * @param serverHttpRequest {@link ServerHttpRequest} servlet request
     */
    public BaseFilter(ServerHttpRequest serverHttpRequest) {
        if (serverHttpRequest instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) serverHttpRequest;
            this.session = servletRequest.getServletRequest().getSession();
        }
    }

    /**
     * Constructor
     *
     * @param session session
     */
    public BaseFilter(HttpSession session) {
        this.session = session;
    }

    /**
     * @param object {@link Object} object which fields will be filtrated
     * @throws FieldAccessException exception of illegal access
     */
    public abstract void filter(Object object) throws FieldAccessException;

    protected HttpSession getSession() {
        return session;
    }

    protected abstract void setConfig(MethodParameter methodParameter);

    public boolean isSessionPropertyExists(String attributeName, String attributeValue) {
        if (session != null) {
            Object sessionObject = session.getAttribute(attributeName);
            return Objects.equals(sessionObject, attributeValue);
        } else
            return false;
    }
}
