package com.json.ignore.strategy;

import com.json.ignore.JsonIgnoreFields;
import org.springframework.core.MethodParameter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;

import javax.servlet.http.HttpSession;

public abstract class JsonIgnore {
    private HttpSession session;
    private ServerHttpRequest serverHttpRequest;
    private MethodParameter methodParameter;

    public JsonIgnore(ServerHttpRequest serverHttpRequest, MethodParameter methodParameter) {
        this.serverHttpRequest = serverHttpRequest;
        this.methodParameter = methodParameter;

        if (serverHttpRequest instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) serverHttpRequest;
            this.session = servletRequest.getServletRequest().getSession();
        }
    }

    public static boolean isAccept(MethodParameter methodParameter) {
        return false;
    }
    public abstract void jsonIgnore(Object object) throws IllegalAccessException;

    public HttpSession getSession() {
        return session;
    }

    public MethodParameter getMethodParameter() {
        return methodParameter;
    }
}
