package com.json.ignore.strategy;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import javax.servlet.http.HttpSession;

public abstract class Ignore {
    private HttpSession session;

    public Ignore(ServerHttpRequest serverHttpRequest) {
        if (serverHttpRequest instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) serverHttpRequest;
            this.session = servletRequest.getServletRequest().getSession();
        }
    }

    public abstract void jsonIgnore(Object object) throws IllegalAccessException;

    public HttpSession getSession() {
        return session;
    }
}
