package com.json.ignore.util;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import javax.servlet.http.HttpSession;

public final class SessionUtil {

    protected SessionUtil() {
    }

    public static HttpSession getSession(ServerHttpRequest serverHttpRequest) {
        if (serverHttpRequest instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) serverHttpRequest;
            return servletRequest.getServletRequest().getSession();
        } else
            return null;
    }
}
