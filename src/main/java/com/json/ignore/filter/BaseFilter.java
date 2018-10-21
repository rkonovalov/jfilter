package com.json.ignore.filter;

import com.json.ignore.FieldAccessException;
import com.json.ignore.util.SessionUtil;
import org.springframework.core.MethodParameter;
import org.springframework.http.server.ServerHttpRequest;
import javax.servlet.http.HttpSession;

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
        this.session = SessionUtil.getSession(serverHttpRequest);
    }

    /**
     * Constructor
     *
     * @param session session
     */
    public BaseFilter(HttpSession session) {
        this.session = session;
    }

    public abstract void filter(Object object) throws FieldAccessException;
    public abstract void filter(Object object, HttpSession session) throws FieldAccessException;
    public abstract void filter(Object object, ServerHttpRequest request) throws FieldAccessException;

    /**
     * Get session from ServletRequest
     * @return {@link HttpSession}
     */
    protected HttpSession getSession() {
        return session;
    }

    protected abstract void setConfig(MethodParameter methodParameter);

    /**
     * Check for exist attribute name and value in session attributes
     * @param attributeName {@link String} attribute name
     * @param attributeValue {@link String} attribute value
     * @return {@link Boolean} returns true if attribute name and value exist in session attributes, else false
     */
    protected boolean isSessionPropertyExists(String attributeName, String attributeValue) {
        return SessionUtil.isSessionPropertyExists(this.session, attributeName, attributeValue);
    }
}
