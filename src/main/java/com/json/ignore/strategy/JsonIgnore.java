package com.json.ignore.strategy;

import org.springframework.core.MethodParameter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;

import javax.servlet.http.HttpSession;

public abstract class JsonIgnore {
    private HttpSession session;

    public JsonIgnore(ServerHttpRequest serverHttpRequest) {
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
