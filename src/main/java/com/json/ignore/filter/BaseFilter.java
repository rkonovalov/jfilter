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

    private SessionUtil sessionUtil;

    /**
     * Default constructor
     *
     * @param serverHttpRequest {@link ServerHttpRequest} servlet request
     */
    public BaseFilter(ServerHttpRequest serverHttpRequest) {
        this.sessionUtil = new SessionUtil(serverHttpRequest);
    }

    /**
     * Constructor
     *
     * @param session session
     */
    public BaseFilter(HttpSession session) {
        this.sessionUtil = new SessionUtil(session);
    }

    public abstract void filter(Object object) throws FieldAccessException;
    public abstract void filter(Object object, HttpSession session) throws FieldAccessException;
    public abstract void filter(Object object, ServerHttpRequest request) throws FieldAccessException;

    /**
     * Get session from ServletRequest
     * @return {@link HttpSession}
     */
    protected HttpSession getSession() {
        return sessionUtil.getSession();
    }

    protected SessionUtil getSessionUtil() {
        return sessionUtil;
    }

    protected abstract void setConfig(MethodParameter methodParameter);

}
