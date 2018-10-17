package com.json.ignore.strategy;

import org.springframework.core.MethodParameter;
import org.springframework.http.server.ServerHttpRequest;

public class JsonIgnoreFactory {

    public static boolean isAccept(MethodParameter methodParameter) {
        return FieldIgnore.isAccept(methodParameter) ||
                StrategyIgnore.isAccept(methodParameter);
    }

    public static JsonIgnore getIgnore(ServerHttpRequest serverHttpRequest, MethodParameter methodParameter) {
        if (FieldIgnore.isAccept(methodParameter)) {
            return new FieldIgnore(serverHttpRequest, methodParameter);
        } else if (StrategyIgnore.isAccept(methodParameter)) {
            return new StrategyIgnore(serverHttpRequest, methodParameter);
        } else
            return null;
    }
}
