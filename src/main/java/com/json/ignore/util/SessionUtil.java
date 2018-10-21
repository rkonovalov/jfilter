package com.json.ignore.util;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import javax.servlet.http.HttpSession;

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
}
