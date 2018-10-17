package com.json.ignore.strategy;

import org.springframework.core.MethodParameter;
import org.springframework.http.server.ServerHttpRequest;

/**
 * Filtration method factory
 */
public class IgnoreFactory {

    /**
     * Check if specified annotations, which accepted in FieldIgnore or StrategyIgnore classes is annotated in method
     * @param methodParameter {@link MethodParameter} Rest method of Rest controller
     * @return true if specified annotations is found
     */
    public static boolean isAccept(MethodParameter methodParameter) {
        return FieldIgnore.isAccept(methodParameter) ||
                StrategyIgnore.isAccept(methodParameter);
    }

    /**
     * Get ignore method by specified annotations in method
     * @param serverHttpRequest servlet request
     * @param methodParameter {@link MethodParameter} Rest method of Rest controller
     * @return {@link FieldIgnore} or {@link StrategyIgnore} if annotations is found, else returns null
     */
    public static Ignore getIgnore(ServerHttpRequest serverHttpRequest, MethodParameter methodParameter) {
        if (FieldIgnore.isAccept(methodParameter)) {
            return new FieldIgnore(serverHttpRequest, methodParameter);
        } else if (StrategyIgnore.isAccept(methodParameter)) {
            return new StrategyIgnore(serverHttpRequest, methodParameter);
        } else
            return null;
    }
}
