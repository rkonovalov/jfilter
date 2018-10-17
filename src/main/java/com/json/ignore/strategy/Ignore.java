package com.json.ignore.strategy;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import javax.servlet.http.HttpSession;

/**
 * This class is base class strategy filtration
 *
 */

public abstract class Ignore {
    /**
     * Http session, may be null
     */
    private HttpSession session;

    /**
     * Default constructor
     * @param serverHttpRequest {@link ServerHttpRequest} servlet request
     */
    public Ignore(ServerHttpRequest serverHttpRequest) {
        if (serverHttpRequest instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) serverHttpRequest;
            this.session = servletRequest.getServletRequest().getSession();
        }
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
