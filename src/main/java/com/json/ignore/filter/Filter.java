package com.json.ignore.filter;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import javax.servlet.http.HttpSession;

/**
 * This class is base class strategy filtration
 *
 */

public abstract class Filter {
    /**
     * Http session, may be null
     */
    private HttpSession session;

    /**
     * Default constructor
     * @param serverHttpRequest {@link ServerHttpRequest} servlet request
     */
    public Filter(ServerHttpRequest serverHttpRequest) {
        if (serverHttpRequest instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) serverHttpRequest;
            this.session = servletRequest.getServletRequest().getSession();
        }
    }

    /**
     * Constructor
     * @param session session
     */
    public Filter(HttpSession session) {
        this.session = session;
    }

    /**
     *
     * @param object {@link Object} object which fields will be filtrated
     * @throws IllegalAccessException exception of illegal access
     */
    public abstract void jsonIgnore(Object object) throws IllegalAccessException;

    public HttpSession getSession() {
        return session;
    }
}
