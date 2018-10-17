package com.json.ignore.filter;

import org.springframework.core.MethodParameter;
import org.springframework.http.server.ServerHttpRequest;

/**
 * Filtration method factory
 */
public class FilterFactory {

    /**
     * Check if specified annotations, which accepted in FieldFilter or StrategyFilter classes is annotated in method
     * @param methodParameter {@link MethodParameter} Rest method of Rest controller
     * @return true if specified annotations is found
     */
    public static boolean isAccept(MethodParameter methodParameter) {
        return FieldFilter.isAccept(methodParameter) ||
                StrategyFilter.isAccept(methodParameter);
    }

    /**
     * Get ignore method by specified annotations in method
     * @param serverHttpRequest servlet request
     * @param methodParameter {@link MethodParameter} Rest method of Rest controller
     * @return {@link FieldFilter} or {@link StrategyFilter} if annotations is found, else returns null
     */
    public static Filter getIgnore(ServerHttpRequest serverHttpRequest, MethodParameter methodParameter) {
        if (FieldFilter.isAccept(methodParameter)) {
            return new FieldFilter(serverHttpRequest, methodParameter);
        } else if (StrategyFilter.isAccept(methodParameter)) {
            return new StrategyFilter(serverHttpRequest, methodParameter);
        } else
            return null;
    }
}
